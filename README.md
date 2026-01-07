# Camel Control Mod

一个Fabric Mod，用于修改Minecraft 1.21.6+中快乐恶魂（Camel）的骑乘控制方式。

## 功能特性

- **原版行为**：按W键向鼠标指向的方向前进（包括垂直方向）
- **自定义行为**：WASD仅水平移动，空格键上升，Ctrl键下降

## 配置

Mod会在 `config/camelcontrol.json` 中生成配置文件：

```json
{
  "horizontalSpeed": 0.5,
  "verticalSpeed": 0.3,
  "enableCustomControls": true
}
```

### 配置说明
- `horizontalSpeed`: 水平移动速度倍率（默认0.5）
- `verticalSpeed`: 垂直移动速度（默认0.3）
- `enableCustomControls`: 是否启用自定义控制（true=启用，false=使用原版控制）

## 安装

1. 确保你有Fabric Loader和Fabric API
2. 下载编译好的jar文件
3. 放入 `.minecraft/mods` 文件夹
4. 启动游戏

## 构建

```bash
./gradlew build
```

构建产物位于 `build/libs/camelcontrol-1.0.0.jar`

## 兼容性

- Minecraft 1.21.6+
- Fabric Loader 0.16.9+
- Fabric API 0.115.0+
- Java 21+

## 许可证

MIT License