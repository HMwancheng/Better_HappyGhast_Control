# Happy Ghast Control

A Fabric mod for Minecraft that provides improved riding controls for Happy Ghasts, allowing full 3D flight control with intuitive keyboard inputs.

## Features

- ✈️ **3D Flight Control**: Ride Happy Ghasts with full directional control
- ⌨️ **Intuitive Controls**:
  - **WASD** - Horizontal movement (forward, backward, strafe left/right)
  - **Space** - Ascend
  - **Left Ctrl** - Descend
- 🎮 **Customizable Keybindings**: Configure controls to your preference
- 🎯 **Precise Targeting**: Only affects Happy Ghasts, normal Ghasts remain unchanged
- 🌐 **Multiplayer Compatible**: Works seamlessly on servers
- ⚡ **Performance Optimized**: Minimal overhead, smooth flight experience

## Installation

### Prerequisites
1. **Minecraft Java Edition** 1.21.4 or later
2. **Fabric Loader** 0.16.11 or later ([Download](https://fabricmc.net/use/))
3. **Fabric API** ([Download](https://modrinth.com/mod/fabric-api))

### Steps
1. Download the latest mod JAR from [Releases](https://github.com/YourUsername/Better_HappyGhast_Control/releases)
2. Place the JAR file in your `.minecraft/mods` folder
3. Ensure Fabric API is also installed in the mods folder
4. Launch Minecraft using the Fabric profile
5. Find a Happy Ghast and right-click to ride!

## Usage

### Riding a Happy Ghast
1. Find a Happy Ghast in the game
2. Right-click on it to mount
3. Use the following controls:

| Action | Default Key | Description |
|--------|-------------|-------------|
| Forward | W | Move forward in the direction you're looking |
| Backward | S | Move backward |
| Strafe Left | A | Move left |
| Strafe Right | D | Move right |
| Ascend | Space | Move upward |
| Descend | Left Ctrl | Move downward |
| Look Around | Mouse | Control direction |

### Configuration

The mod creates a configuration file at `config/happyghast_control.json`:

```json
{
  "keybindings": {
    "ascend": "key.keyboard.space",
    "descend": "key.keyboard.left.control"
  },
  "movementMultiplier": 1.0,
  "enableVerticalControl": true,
  "requireShiftForDescend": false
}
```

**Configuration Options**:
- `movementMultiplier`: Adjust movement speed (default: 1.0)
- `enableVerticalControl`: Enable/disable vertical movement (default: true)
- `requireShiftForDescend`: Require Shift key for descending (default: false)

### Keybinding Customization

1. Open Minecraft's Controls menu
2. Navigate to the "Happy Ghast Controls" category
3. Customize the Ascend and Descend keys as desired
4. Changes take effect immediately

## Building from Source

### Requirements
- Java Development Kit (JDK) 21
- Git

### Build Steps

1. Clone the repository:
```bash
git clone https://github.com/YourUsername/Better_HappyGhast_Control.git
cd Better_HappyGhast_Control
```

2. Build the mod (Windows):
```bash
gradlew.bat build
```

Or on Linux/Mac:
```bash
./gradlew build
```

3. Find the compiled JAR in `build/libs/`

### GitHub Actions Build

This project uses GitHub Actions for automated builds. Every push to `main` or `develop` branches triggers a build, and you can download the compiled JAR from the Actions tab.

## Compatibility

- **Minecraft Version**: 1.21.4+ (Happy Ghasts introduced in 1.21.6 snapshots)
- **Fabric Loader**: 0.16.11+
- **Fabric API**: Required
- **Client/Server**: Client-side mod (server installation not required, but compatible)

## Known Issues

- Happy Ghasts are only available in Minecraft 1.21.6+ snapshots
- If targeting stable releases, ensure you're using a version where Happy Ghasts exist

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Development Setup

1. Fork and clone the repository
2. Import the project into IntelliJ IDEA (recommended)
3. Run `./gradlew genSources` to generate Minecraft source code
4. Run the mod in development mode: `./gradlew runClient`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Credits

- **Developer**: HM_wancheng
- **Framework**: Fabric
- **Inspired by**: Community requests for better flying mob controls

## Support

If you encounter any issues or have suggestions:
- Open an issue on [GitHub Issues](https://github.com/YourUsername/Better_HappyGhast_Control/issues)
- Join our community discussions

## Changelog

### Version 1.0.0 (Initial Release)
- ✨ Custom riding controls for Happy Ghasts
- 🎮 WASD horizontal movement
- ⬆️ Space for ascending
- ⬇️ Ctrl for descending
- ⚙️ Configurable keybindings
- 🌐 Multiplayer support
- 🇨🇳 Chinese language support

---

Made with ❤️ for the Minecraft community
