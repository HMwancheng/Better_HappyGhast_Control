# Contributing to Happy Ghast Control

Thank you for your interest in contributing to Happy Ghast Control! This document provides guidelines for contributing to the project.

## Code of Conduct

- Be respectful and constructive
- Welcome newcomers and help them learn
- Focus on what's best for the community and the project

## How to Contribute

### Reporting Bugs

If you find a bug, please open an issue with:
- A clear, descriptive title
- Detailed steps to reproduce the issue
- Expected vs. actual behavior
- Minecraft version, Fabric Loader version, and Fabric API version
- Any relevant logs or screenshots

### Suggesting Features

Feature suggestions are welcome! Please:
- Check if the feature has already been requested
- Explain the use case and benefit
- Provide examples if applicable

### Submitting Code

1. **Fork the Repository**
   ```bash
   git clone https://github.com/YourUsername/Better_HappyGhast_Control.git
   cd Better_HappyGhast_Control
   ```

2. **Create a Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Comment your code where necessary
   - Keep changes focused and atomic

4. **Test Your Changes**
   - Run `./gradlew build` to ensure the mod compiles
   - Test in-game thoroughly
   - Verify compatibility with both single-player and multiplayer

5. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "feat: Add your feature description"
   ```
   
   Use conventional commit messages:
   - `feat:` for new features
   - `fix:` for bug fixes
   - `docs:` for documentation changes
   - `refactor:` for code refactoring
   - `test:` for adding tests
   - `chore:` for maintenance tasks

6. **Push and Create Pull Request**
   ```bash
   git push origin feature/your-feature-name
   ```
   Then create a PR on GitHub with a clear description of your changes.

## Development Setup

### Prerequisites
- JDK 21
- Git
- IntelliJ IDEA (recommended) or Eclipse

### Setup Steps

1. **Clone and Import**
   ```bash
   git clone https://github.com/YourUsername/Better_HappyGhast_Control.git
   cd Better_HappyGhast_Control
   ```

2. **Generate Sources**
   ```bash
   ./gradlew genSources
   ```

3. **Import in IntelliJ IDEA**
   - Open IntelliJ IDEA
   - File → Open → Select the project folder
   - Import as Gradle project

4. **Run Development Client**
   ```bash
   ./gradlew runClient
   ```

## Code Style Guidelines

- **Indentation**: Use tabs (4 spaces)
- **Naming**:
  - Classes: PascalCase
  - Methods/Variables: camelCase
  - Constants: UPPER_SNAKE_CASE
- **Documentation**: Add JavaDoc comments for public methods and classes
- **Imports**: Organize and remove unused imports
- **Line Length**: Keep lines under 120 characters when practical

## Mixin Guidelines

When working with mixins:
- Use `@Inject` with minimal invasiveness
- Avoid `@Overwrite` unless absolutely necessary
- Document the purpose of each mixin
- Test compatibility with other popular mods
- Use proper targeting to minimize conflicts

## Testing Checklist

Before submitting a PR, verify:
- [ ] Code compiles without errors (`./gradlew build`)
- [ ] Mod loads in Minecraft without crashes
- [ ] Happy Ghast controls work as expected
- [ ] Normal Ghasts are unaffected
- [ ] Multiplayer functionality works
- [ ] No errors in console logs
- [ ] Configuration system works correctly
- [ ] Keybindings can be customized

## Pull Request Review Process

1. A maintainer will review your PR within a few days
2. Address any requested changes
3. Once approved, your PR will be merged
4. Your contribution will be credited in the changelog

## Questions?

If you have questions about contributing:
- Open a discussion on GitHub
- Check existing issues and PRs for similar topics
- Reach out in the community channels

Thank you for contributing to Happy Ghast Control! 🎮✨
