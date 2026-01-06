# Happy Ghast Control

A Fabric Mod for Minecraft 1.21.8 that modifies the riding control logic for Happy Ghasts while keeping normal Ghasts unchanged.

## Features

- **Modified Control Logic**: 
  - Use WASD/arrow keys for horizontal movement
  - Space bar to ascend
  - Ctrl key to descend
- **Configurable Key Bindings**: Customize controls through Minecraft's key binding settings
- **No Impact on Normal Ghasts**: Only modifies happy ghast behavior
- **Original Speed Preserved**: Maintains vanilla movement speed
- **Smooth Controls**: Responsive and lag-free

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.8
2. Download the latest version of [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Download the latest HappyGhastControl.jar from the [Releases](https://github.com/yourusername/happyghastcontrol/releases) page
4. Place all JAR files in your Minecraft `mods` folder
5. Launch Minecraft with the Fabric profile

## Configuration

Key bindings can be customized in-game:

1. Go to `Options` > `Controls`
2. Scroll down to the `Happy Ghast Control` category
3. Click on the key bindings to change them to your preference

## Default Key Bindings

- **Ascend**: Space Bar
- **Descend**: Left Ctrl
- **Horizontal Movement**: WASD/Arrow Keys

## Building from Source

### Prerequisites

- Java 21 or newer
- Git

### Build Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/happyghastcontrol.git
   cd happyghastcontrol
   ```

2. Build the mod using Gradle:
   ```bash
   ./gradlew build
   ```

3. The built JAR file will be available in `build/libs/`

## Development

### Setup

1. Clone the repository as described above
2. Import the project into your IDE (IntelliJ IDEA or Eclipse)
3. Run the `genSources` task to generate source code
4. Start developing!

### Testing

Use the `runClient` Gradle task to launch a Minecraft client with the mod installed for testing.

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Make your changes
4. Test your changes
5. Submit a pull request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Credits

- Developed for Minecraft 1.21.8 with Fabric API
- Uses Fabric Loom for build system
