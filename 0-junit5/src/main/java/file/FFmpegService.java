package file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FilenameUtils;


/**
 * FFmpeg를 사용하여 비디오 트랜스코딩.
 * <p>
 * FFmpeg 명령어를 Java ProcessBuilder를 사용하여 실행한다.
 * </p>
 *
 * @see <a href="https://gist.github.com/jsturgis/3b19447b304616f18657">테스트용 비디오</a>
 */
public class FFmpegService {

    public int execute(
            final String inputFilePath,
            final String outputFilePath
    ) throws IOException, InterruptedException {
        /*
        ffmpeg -y \
            -i $input \
            -map 0:v:0 -map 0:a:0 \
            -map 0:v:0 -map 0:a:0 \
            -map 0:v:0 -map 0:a:0 \
            -c:v libx264 -crf 22 -c:a aac -ar 44100 \
            -filter:v:0 scale=w=-2:h=360  -maxrate:v:0 600k  -b:a:0 500k \
            -filter:v:1 scale=w=-2:h=480  -maxrate:v:1 1500k -b:a:1 1000k \
            -filter:v:2 scale=w=-2:h=720 -maxrate:v:2 3000k -b:a:2 2000k \
            -var_stream_map "v:0,a:0,name:360p v:1,a:1,name:480p v:2,a:2,name:720p" \
            -preset faster \
            -threads 0 \
            -f hls \
            -hls_time 10 \
            -hls_list_size 0 \
            -hls_flags independent_segments \
            -master_pl_name "$playlist_name.m3u8" \
            "$playlist_name-%v.m3u8"
         */
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-y",
                "-i", inputFilePath,
                "-map", "0:v:0", "-map", "0:a:0",
                "-map", "0:v:0", "-map", "0:a:0",
                "-map", "0:v:0", "-map", "0:a:0",
                // video codec
                "-c:v", "libx264", "-crf", "22",
                // audio codec
                "-c:a", "aac", "-ar", "44100",
                // video filter
                "-filter:v:0", "scale=w=-2:h=360", "-maxrate:v:0", "600k", "-b:a:0", "500k",
                "-filter:v:1", "scale=w=-2:h=480", "-maxrate:v:1", "1500k", "-b:a:1", "1000k",
                "-filter:v:2", "scale=w=-2:h=720", "-maxrate:v:2", "3000k", "-b:a:2", "2000k",
                // output options
                // Java ProcessBuilder에서는 따옴표를 파싱할 수 없음. (ex: "\"")
                // -var_stream_map "v:0,a:0,name:360p v:1,a:1,name:480p v:2,a:2,name:720p"
                "-var_stream_map", "v:0,a:0,name:360p v:1,a:1,name:480p v:2,a:2,name:720p",
                "-preset", "faster",
                "-threads", "0",
                // HLS options
                "-f", "hls",
                "-hls_time", "10", // 세그먼트 길이 (second)
                "-hls_list_size", "0", // 0: 모든 세그먼트 포함 (default 5)
                "-hls_flags", "independent_segments", // 세그먼트가 독립적임을 보장 (키 프레임으로 시작되도록)
                "-master_pl_name", FilenameUtils.getBaseName(outputFilePath) + ".m3u8",
                outputFilePath + "-%v.m3u8"
        );

        // stderr도 stdout으로 합치기
        builder.redirectErrorStream(true);

        // builder = new ProcessBuilder("ffmpeg", "-version"); // 버전 확인
        // 명령어 미리 보기
        System.out.println(String.join(" ", builder.command()));

        // 명령어 실행
        Process process = builder.start();

        // stdout 읽기
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            reader.lines().forEach(System.out::println);
        }
        return process.waitFor();
    }

}
