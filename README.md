# Political Survey AI

This command-line Java application uses a Naive Bayes machine learning model from the WEKA library to predict a user's political affiliation based on their responses to a short survey. Over time, the system improves by appending new responses and actual party labels to a CSV file for retraining.

## 🚀 Getting Started

### Prerequisites

- Java 8+
- [Weka library (stable 3.8.5)](https://www.cs.waikato.ac.nz/ml/weka/downloading.html)
- A terminal or command prompt

### Files

- `PoliticalSurveyAI.java` – The main Java program
- `survey_data.csv` – The dataset file (must be in the same directory)

### Setup

1. Clone or download this repository.
2. Download Weka from Weka's official site and place the .jar (e.g., weka-stable-3-8-5.jar) in a lib folder.
3. Ensure you have a survey_data.csv file with this format:
   ```bash
   answer1,answer2,answer3,answer4,answer5,Party
   1,2,3,2,1,Liberal

### Run the Program

1. Compile the program:
   ```bash
   javac -cp ".:./lib/weka-stable-3-8-5.jar" PoliticalSurveyAI.java

2. Run the program:
 ```bash
   java --add-opens java.base/java.lang=ALL-UNNAMED -cp ".:./lib/weka-stable-3-8-jar" PoliticalSurveyAI

   # Note for Windows users: Replace : with ; in the classpath:

