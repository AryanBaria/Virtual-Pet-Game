# 💙 Virtual Pet Game

## 📝 Overview  
**Virtual Pet Game** is a Java-based virtual pet simulator created by **Aryan Baria, Dilraj Deogan, Maher Rammal, Mohammed Bayoumi, and Rayan Amir** 
Users can interact with a virtual pet through feeding, playing, sleeping, and visiting the vet. The game uses **Java Swing GUI**, includes a **save/load system**, **tutorial screens**, and **parental control features**.

---

## ⚙️ Requirements

- **Java JDK** version **17 or higher**
- **Visual Studio Code (VS Code)** – *Recommended IDE*
- **Java Extension Pack** (VS Code extension)

---

## 🔨 How to Build

1. **Install JDK 17+**  
   📎 [Download from Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. **Install Visual Studio Code**  
   📎 [Download VS Code](https://code.visualstudio.com)

3. **Install Java Extension Pack** in VS Code

4. **Clone the repository:**
   ```bash
   git clone https://gitlab.sci.uwo.ca/courses/2025/01/COMPSCI2212/group54.git
   cd group54
   ```

5. **Open the folder** in **VS Code**

6. Open `Main.java` and click **Run Java**

---

## ▶️ How to Run the Game

1. Open `Main.java` in VS Code  
2. Click **Run Java**  
3. The game will launch in a new window

---

## 🎮 User Guide

### 🟦 Welcome Screen
Options:
- **Start New Game**
- **Load Game** – Resume a saved session
- **Parental Screen** – Password protected (`2212`)
  - Set time/play restrictions
  - Click **Back** to return
- **Exit** – Closes the application

### 🟦 Tutorial Screens
- Choose a pet
- View step-by-step instructions

### 🟦 Main Gameplay Screen
- Pet image and interactive screen
- View stats: `Happiness`, `Sleep`, `Hunger`, `Health`
- Use action buttons:
  - **Go To Sleep**
  - **Feed**
  - **Play Games**
  - **Take to Vet**
  - **Save Game** (to file)
  - **Exit**
  - **Inventory** (view available gifts and food)

---

## 🔐 Login, Passwords, or PIN

- ❌ No login or account system required  
- 🔑 **Parental Controls Password:** `2212`  
- ✅ No additional passwords, usernames, or pins needed

---

## 👨‍👩‍👧 Parental Controls

### Accessing:
- Click **Parental Screen**
- Enter password: `2212`

### Features:
- Set **time restrictions**
- Set **daily play limits**
- Click **Back** to return

### Run Directly:
1. Open `ParentalControlScreen.java`  
2. Click **Run Java**

---

## 📁 Save System

Game data is saved to:
- `save1.txt`
- `save2.txt`
- `save3.txt`  
*(Files are created in the root directory)*

---

## ✅ Executable File
The compiled executable version of the game is located in:
Official_PetGame_Finale/Executable File/
You can run the game directly from this folder without recompiling the source.

---

## 📄 JavaDoc ZIP Archive
The generated JavaDoc files are zipped and included in:
Official_PetGame_Finale/javadoc.zip
This archive contains the full HTML documentation for the project, as per submission requirements.

---

## 🧪 How to Run Unit Tests

To run the JUnit 5 unit tests manually, ensure the JUnit platform `.jar` files are in a `lib/` directory.

### 1. Download Required JARs
Download the following JARs and place them in a folder named `lib/` in the root directory:
- `junit-platform-console-standalone-1.10.0.jar`  
(Download from: https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/)

### 2. Compile All Test Files
``bash 
javac -cp .:lib/junit-platform-console-standalone-1.10.0.jar -d bin test/*.java src/*.java

### 3. Run All Tests

java -jar lib/junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path

If you're on Windows, replace : with ; in the -cp classpath:

---

## 👥 Development Team  
- Aryan Baria  
- Dilraj Deogan  
- Maher Rammal  
- Mohammed Bayoumi  
- Rayan Amir  

