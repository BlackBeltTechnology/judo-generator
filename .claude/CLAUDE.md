# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Full build and deploy
mvn clean deploy -s settings.xml

# Build without deploying
mvn clean package

# Build a single module
mvn -pl hu.blackbelt.judo.generator.formatter.sql clean package

# Run tests (only the ANTLR parser module has tests)
mvn -pl hu.blackbelt.judo.generator.parser.derived.expression.sql test

# Run a single test class
mvn -pl hu.blackbelt.judo.generator.parser.derived.expression.sql -Dtest=DerivedAttributeExpressionToRdbmsModelParserTest test

# Build with PSM transformation profile enabled
mvn -Ppsm-transformer-development clean package
```

## Architecture

This is a **model-driven code generation framework** that transforms business models through multiple abstraction levels to generate enterprise Java applications.
We can give it a PIM(platform independent model) and it first transforms it to a PSM(platform specific model) with the help of the PSM metamodel.
Then from the PSM it generates models for rdbms, java, querydsl, etc with the help of their metamodels. 
And from these models it generates files inside the template modules from the templates. 

### Transformation Pipeline

```
PIM (UML/business model)
    ↓ transformer.psm  [Epsilon ETL]
PSM (platform-specific model)
    ├─ transformer.java     → templates.java     → Java source (Spring, Hibernate)
    ├─ transformer.rdbms    → templates.rdbms     → SQL DDL
    ├─ transformer.querydsl → templates.querydsl  → QueryDSL API
    ├─ transformer.ui       → templates.ui        → UI definitions
    ├─ transformer.permission → templates.permission → security config
    └─ transformer.migration  → templates.migration → DB migration SQL
```

### Module Naming Convention

All modules follow the pattern `hu.blackbelt.judo.generator.<category>.<name>`:

- **`meta.*`** — EMF/Ecore metamodel definitions (`.ecore` files); one per domain (psm, pim, java, rdbms, ui, querydsl, permission, odata, sabre)
- **`transformer.*`** — Model-to-model transformations written in Epsilon ETL (`.etl`) with optional Epsilon Validation Language (`.evl`) rules
- **`templates.*`** — Code generation templates written in Epsilon EGL (`.egl`)
- **`parser.derived.expression.sql`** — ANTLR4-based SQL expression parser for derived attributes; the only module with JUnit tests
- **`formatter.sql`** — SQL formatting utility
- **`pim.extension`** — Extensions to the PIM metamodel

### Key Technologies

- **Epsilon Framework** — ETL for model-to-model transforms, EGL for code generation templates, EVL for validation
- **EMF/Ecore** — metamodel definitions and runtime
- **ANTLR4** — parses SQL expressions in derived attribute definitions
- **Eclipse Tycho** — OSGi/Eclipse plugin build support (hence `.project` and manifest files)
- **Java 8** throughout

### Test Model

The integration tests use `anakin-model` (v24.1.0-1257 by default). The parent POM has commented-out alternatives (Sparkdom, Car). To switch test models, update the `anakin-model.version` property in `hu.blackbelt.judo.generator.parent/pom.xml`.

### Type Mapping Configuration

PIM-to-PSM type mappings are defined in Excel spreadsheets (`PIM2PSM.xlsx`, `AccessControl.xlsx`) inside the transformer modules, not in code. Changes to type mappings require editing these spreadsheets.

### CI/CD

GitHub Actions (`.github/workflows/build.yaml`) builds with JDK 8 on Ubuntu and deploys to Google Cloud Artifact Registry. Artifact versioning uses a BUILD_NUMBER offset (+600). The `mvn-settings.xml` file contains the artifact registry wagon configuration required for deployment.
