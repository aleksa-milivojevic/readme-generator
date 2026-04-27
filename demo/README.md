# AI Powered README Generator

**AI Powered README Generator** is a powerful IntelliJ IDEA plugin that leverages the power of Google Gemini AI models to automatically generate professional and comprehensive documentation for your projects.

Forget about writing `README.md` files from scratch – let AI analyze your code, identify technologies, and create a perfect project overview in seconds.

---

## Features

* **Code Analysis:** Reads the src directory from your project.
* **AI Integration:** Utilizes Google model (`Gemini 2.5 Flash Lite`) to generate technically accurate text.
* **Backgroud Worker:** Works as a separate thread so you can continue with your final touches while your README is being generated.

---

##  How to Use

### 1. Set Up Your API Key
Before first use, you need to add your API key:
* Obtain a key at [Google AI Studio](https://aistudio.google.com/).
* In IntelliJ, go to `Edit Configurations...` (drop down menu next to Run ▷) > `Environment variables` and add it under `GEMINI_API_KEY`.

### 2. Generate Documentation
* Right-click on the root directory of your project.
* Go to **Code** tab, select **Generate README** option.
* Wait a few seconds while the AI analyzes the project.
* Review the result and make any edits if necessary.


##  Privacy and Security

The plugin only sends necessary metadata (files from src directory) to the Google Gemini API. Your code is not permanently stored or used for public model training, subject to standard API terms of service.

---

## License

This plugin is not licenced.

---

## Contributing

Currently not open for contributions

## Post Scriptum

This plugin is a project intended for a JetBrains internship application. It is not verified nor available on JetBrains Marketplace.
