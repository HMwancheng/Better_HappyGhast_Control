# Setup and Build Guide

This guide will help you complete the setup and build the Happy Ghast Control mod.

## Prerequisites Check

Before you begin, ensure you have:
- ✅ Java Development Kit (JDK) 21 installed
- ✅ Git installed (already initialized in this project)
- ✅ IntelliJ IDEA (recommended) or another Java IDE

## Step 1: Generate Gradle Wrapper

Since Gradle wrapper files need to be generated, run the following command:

### If you have Gradle installed globally:
```bash
gradle wrapper --gradle-version 8.10.2
```

### If you don't have Gradle installed:
The easiest way is to use IntelliJ IDEA:
1. Open this project folder in IntelliJ IDEA
2. IntelliJ will automatically detect the Gradle build file
3. Let it sync and download dependencies
4. The wrapper will be generated automatically

## Step 2: Initial Build

Once the wrapper is generated, build the mod:

```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

## Step 3: Test in Development Environment

Run the Minecraft client in development mode:

```bash
# Windows
gradlew.bat runClient

# Linux/Mac
./gradlew runClient
```

## Step 4: Push to GitHub

After verifying the build works, push to GitHub:

```bash
# Create a new repository on GitHub first, then:
git remote add origin https://github.com/YourUsername/Better_HappyGhast_Control.git
git branch -M main
git push -u origin main
```

## Step 5: Verify GitHub Actions

After pushing:
1. Go to your GitHub repository
2. Click on the "Actions" tab
3. Verify that the build workflow runs successfully
4. Download the compiled JAR from the workflow artifacts

## Important Notes

### About Minecraft Version
⚠️ **Version Compatibility Notice**: This mod is configured for Minecraft 1.21.4, but Happy Ghasts were introduced in Minecraft 1.21.6 snapshots (25w15a). 

**To use this mod, you have two options:**

1. **Wait for Happy Ghasts in stable release**: Update `gradle.properties` when Happy Ghasts are available in a stable Minecraft release

2. **Use snapshot versions now**: Update `gradle.properties` to target a snapshot version:
   ```properties
   minecraft_version=1.21.6-alpha.25.15.a
   yarn_mappings=1.21.6-alpha.25.15.a+build.1
   ```
   (Check [Fabric versions](https://fabricmc.net/develop/) for exact version strings)

### Testing Without Happy Ghasts

If you want to test the mod on 1.21.4 (where Happy Ghasts don't exist yet):
- The mod will load successfully
- It simply won't do anything since there are no Happy Ghasts to ride
- All other mobs (including normal Ghasts) will behave normally

### Build Outputs

After a successful build, you'll find:
- **Compiled mod JAR**: `build/libs/happyghast-control-1.0.0.jar`
- **Sources JAR**: `build/libs/happyghast-control-1.0.0-sources.jar`

## Troubleshooting

### Build fails with "Could not find tools.jar"
- Ensure JDK 21 is installed (not just JRE)
- Set `JAVA_HOME` environment variable to your JDK installation

### Mixin errors during build
- Ensure Fabric Loader and API versions match your Minecraft version
- Check `gradle.properties` for correct version numbers

### Mod doesn't load in Minecraft
- Verify Fabric Loader is installed in your Minecraft launcher
- Check that Fabric API is in your mods folder
- Review the Minecraft logs for errors

## Next Steps After Setup

1. **Create a release**: Tag your commit with `v1.0.0` and push the tag
2. **Test in-game**: Spawn a Happy Ghast and test the controls
3. **Adjust settings**: Modify speed multipliers and controls as needed
4. **Share**: Publish to CurseForge or Modrinth

## Development Workflow

For ongoing development:

1. **Make changes** to Java files
2. **Build**: `./gradlew build`
3. **Test**: `./gradlew runClient`
4. **Commit**: `git commit -am "description"`
5. **Push**: `git push`
6. **GitHub Actions** will automatically build and create artifacts

## Getting Help

If you encounter issues:
- Check the [Fabric documentation](https://fabricmc.net/wiki/)
- Review Minecraft logs in `run/logs/latest.log`
- Open an issue on GitHub

---

Happy modding! 🎮✨
