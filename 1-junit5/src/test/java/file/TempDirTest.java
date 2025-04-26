package file;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TempDir
 *
 * <ol>
 *     <li>Windows: C:\Users\%USERNAME%\AppData\Local\Temp\junit-13352290513338801033\</li>
 *     <li>Linux: /tmp/junit-13352290513338801033/</li>
 *     <li>macOS: /var/folders/2g/0xq3j1s12xg4v8r7c5bq9f3m0000gn/T/junit-13352290513338801033/</li>
 * </ol>
 */
@Tag("slow")
public class TempDirTest {

    private static final List<String> RESOLUTIONS = List.of("360p", "480p", "720p");

    private FFmpegService ffmpegService;

    @TempDir(cleanup = CleanupMode.ALWAYS)
    private Path tempDir;

    @BeforeEach
    void setUpOnlyIfFFmpegExists() throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        String[] cmd = os.contains("win") ? new String[]{"where", "ffmpeg"} : new String[]{"which", "ffmpeg"};
        Process process = new ProcessBuilder(cmd).start();
        Assumptions.assumeTrue(process.waitFor() == 0, "ffmpeg 없음, 테스트 skip");

        ffmpegService = new FFmpegService();
    }

    @DisplayName("ffmpeg 비디오 변환")
    @Test
    void testFfmpegCommand() throws InterruptedException, IOException, URISyntaxException {
        // given
        URL inputResource = getClass().getClassLoader().getResource("ForBiggerEscapes.mp4");
        String inputFilePath = Paths.get(inputResource.toURI()).toFile().getAbsolutePath();

        Path outputPath = tempDir.resolve("output");
        String outputFilePath = outputPath.toString();

        // when
        int exitCode = ffmpegService.execute(inputFilePath, outputFilePath);

        // then
        assertThat(exitCode).isZero();
        // HLS 파일들 검증
        try (Stream<Path> files = Files.list(tempDir)) {
            List<Path> outputFiles = files.toList();

            // 메인 플레이리스트 확인
            assertThat(outputFiles)
                    .anyMatch(p -> p.getFileName().toString().equals("output.m3u8"));

            // 각 해상도별 플레이리스트와 세그먼트 확인
            for (String resolution : RESOLUTIONS) {
                // m3u8 파일 존재 확인
                String playlistName = String.format("output-%s.m3u8", resolution);
                assertThat(outputFiles)
                        .anyMatch(p -> p.getFileName().toString().equals(playlistName));

                // ts 파일들 존재 확인
                assertThat(outputFiles)
                        .anyMatch(p -> p.getFileName().toString().startsWith("output-" + resolution)
                                && p.getFileName().toString().endsWith(".ts"));
            }

            // 모든 파일이 비어있지 않은지 확인
            assertThat(outputFiles)
                    .allMatch(p -> {
                        try {
                            return Files.size(p) > 0;
                        } catch (IOException e) {
                            return false;
                        }
                    });
        }

    }

}
