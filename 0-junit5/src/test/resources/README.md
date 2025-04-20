# Testing FFmpeg

## Install FFmpeg

```shell
# macOS
brew install ffmpeg
```

```shell
# Windows 11 (관리자 권한으로 실행)
choco install ffmpeg-full
```

```shell
# Debian (sudo 권한 필요)
apt install ffmpeg
```

```shell
# Red Hat
cd ~
wget https://johnvansickle.com/ffmpeg/releases/ffmpeg-release-amd64-static.tar.xz
mkdir ffmpeg-release-amd64-static
tar -xf ffmpeg-release-amd64-static.tar.xz --strip-components=1 -C ffmpeg-release-amd64-static
rm -f ffmpeg-release-amd64-static.tar.xz
sudo ln -s ~/ffmpeg-release-amd64-static/ffmpeg /usr/local/bin/ffmpeg
ffmpeg -version
```

## 테스트 대상 비디오 용량 줄이기

```shell
ffmpeg -y \
  -i input.mp4 \
  -vcodec libx264 \
  -crf 30 \
  -preset veryslow \
  -vf "scale=-2:360,fps=15" \
  -acodec aac \
  -b:a 64k \
  output.mp4
```
