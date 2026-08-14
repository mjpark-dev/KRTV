# KRTV

KRTV 是面向 Android TV 的韩国电视直播播放器，支持遥控器操作、M3U 播放列表、频道收藏、远程配置和应用内更新。

## 当前内置直播源

KRTV 默认只包含项目维护者自己的播放列表：

```text
https://raw.githubusercontent.com/mjpark-dev/iptv/refs/heads/master/korean.m3u
```

播放列表内容由独立仓库维护，KRTV 仓库不保存频道视频流。使用者应自行确认所在地区的网络访问条件及相关内容授权。

## 操作方式

- 遥控器中键：打开频道列表
- 遥控器右键：打开设置
- 遥控器左键：打开节目单
- 遥控器返回键：关闭当前菜单
- 聚焦频道标题后按右键：收藏或取消收藏
- 默认使用遥控器上下键切换频道，可在设置中反转换台方向

## 直播源格式

支持 M3U、TXT 和 JSON。M3U 示例：

```m3u
#EXTM3U
#EXTINF:-1 tvg-id="channel-id" tvg-name="频道名称" group-title="韩国电视",频道名称
https://example.com/live.m3u8
```

KRTV 当前没有内置 EPG 地址。需要节目单时，可在设置中填写与频道 `tvg-id` 匹配的 XMLTV 地址。

## 远程配置

应用与电视处于同一局域网时，可在设置页面查看远程配置地址。默认端口为 `34567`。

## 构建

项目要求 JDK 21，并通过 Gradle Wrapper 构建：

```shell
./gradlew clean testDebugUnitTest assembleRelease
```

生成目录：

```text
app/build/outputs/apk/release/
```

## 发布签名

发布版必须始终使用同一份签名密钥。GitHub Actions 发布流程需要配置以下仓库 Secrets：

- `KEYSTORE`：Base64 编码的密钥库
- `KEYSTORE_PASSWORD`
- `ALIAS`
- `ALIAS_PASSWORD`

推送 `v*` 标签会构建签名 APK 并创建 GitHub Release。首版标签建议使用 `v1.0.0`。

## 下载

正式版本发布后可从 [GitHub Releases](https://github.com/mjpark-dev/KRTV/releases) 下载。

## 许可证

本项目遵循 [MIT License](LICENSE)。依据许可证要求，原始版权声明保留在许可证文件中。
