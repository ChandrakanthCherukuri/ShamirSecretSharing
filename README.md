# 🔐 Shamir's Secret Sharing - Polynomial Secret Finder

A Java program to reconstruct the constant term (`c`) of a polynomial using simplified Shamir’s Secret Sharing logic. Given encoded (x, y) roots in various bases, the code decodes the values and uses **Lagrange interpolation** to extract the original secret.

---

## 📂 Project Structure

📁 project-root/
├── 📁 resources/
│ └── input.json ← JSON file with test cases
├── 📁 lib/ ← (Optional) External libraries like org.json
├── SecretFinder.java ← Main Java file
├── README.md ← This file


---


## 🧠 How It Works
✅ Read JSON input file.

🔢 Decode y values based on their base.

📈 Apply Lagrange Interpolation on the (x, y) points.

🎯 Extract the constant term c of the polynomial — the secret.

## 📌 Prerequisites
Java 8 or above

org.json library for parsing JSON

Add manually or via Maven:

xml
Copy
Edit
<dependency>
  <groupId>org.json</groupId>
  <artifactId>json</artifactId>
  <version>20210307</version>
</dependency>

## ▶️ How to Run
Add the input.json file under the resources/ folder.

Compile and run SecretFinder.java in IntelliJ or your IDE:

nginx
Copy
Edit
javac SecretFinder.java
java SecretFinder

## ✅ Output
Prints the decoded constant c (the secret) for each test case in the JSON file.

rust
Copy
Edit
Secret for Testcase 1: 23
Secret for Testcase 2: 15

## 🚀 Submission
✅ Push all files (code + input.json + README.md) to GitHub


## 💡 Author
Chandrakanth Cherukuri
B.Tech CSE – Full Stack & Software Dev
Lovely Professional University
