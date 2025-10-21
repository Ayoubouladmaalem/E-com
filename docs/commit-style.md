# Commit Message Style Guide

All team members must follow this commit message convention for every commit. This ensures clarity, traceability, and consistency across the project.

## Format

```
<type>(scope): <short description>

```

### Types
- feat: A new feature
- fix: A bug fix
- docs: Documentation only changes
- refactor: A code change that neither fixes a bug nor adds a feature
- test: Adding missing tests or correcting existing tests
- chore: Changes to the build process or auxiliary tools and libraries

### Scope
- Indicate the module or feature affected (e.g., user, catalog, order, ai, admin)

### Example
```
feat(user): add JWT authentication
fix(order): correct total calculation
chore(devops): update CI pipeline
```

## Pull Request Policy
- Always create pull requests to the `develop` branch, **never** to `main`.
- PR title should follow the same commit style.
- At least one code review is required before merging.

---
