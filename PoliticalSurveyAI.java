import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.converters.CSVLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

public class PoliticalSurveyAI {
    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to political party guesser! I will ask you 5 questions and then I will predict your political party. \n");
        while (true) {
            ArrayList<Integer> answers = getAnswers();
            int answer1 = answers.get(0);
            int answer2 = answers.get(1);
            int answer3 = answers.get(2);
            int answer4 = answers.get(3);
            int answer5 = answers.get(4);

            Instances data = loadData();

            NaiveBayes model = train(data);

            double prediction = predictParty(data, model, answer1, answer2, answer3, answer4, answer5);

            System.out.println("Predicted Political Party: " + data.classAttribute().value((int) prediction));

            String actualParty = getActualParty();

            appendToCSV(answer1, answer2, answer3, answer4, answer5, actualParty);

            System.out.print("\nWould you like to take the survey again? (yes/no): \n");

            Scanner scanner = new Scanner(System.in);
            String response = scanner.next().toLowerCase();
            if (!response.equals("yes")) {
                System.out.println("Thanks for participating!");
                scanner.close();
                break;
            }
        }
    }

    // Takes user through survey
    public static ArrayList<Integer> getAnswers() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> answers = new ArrayList<>();
        System.out.print(
                "What is your stance on government-funded healthcare? \n1: Strongly Support\n2: Support\n3: Neutral\n4: Oppose\n5: Strongly Oppose\n");
        answers.add(scanner.nextInt());

        System.out.print(
                "What is your stance on taxes being raised for high-income earners? \n1: Strongly Support\n2: Support\n3: Neutral\n4: Oppose\n5: Strongly Oppose\n");
        answers.add(scanner.nextInt());

        System.out.print(
                "What is your stance on implementing stricter gun control laws? \n1: Strongly Support\n2: Support\n3: Neutral\n4: Oppose\n5: Strongly Oppose\n");
        answers.add(scanner.nextInt());

        System.out.print(
                "What is your stance on the government taking action on climate change, even if it negativly affects the economy? \n1: Strongly Support\n2: Support\n3: Neutral\n4: Oppose\n5: Strongly Oppose\n");
        answers.add(scanner.nextInt());

        System.out.print(
                "What is your stance on decriminalizing marijuana? \n1: Strongly Support\n2: Support\n3: Neutral\n4: Oppose\n5: Strongly Oppose\n");
        answers.add(scanner.nextInt());

        return answers;
    }

    // Loads data from CSV file into a WEKA "Instance"
    public static Instances loadData() throws Exception {

        CSVLoader loader = new CSVLoader();
        loader.setSource(new java.io.File("survey_data.csv")); // Path to CSV file
        Instances data = loader.getDataSet(); // Sets name of data to "data"

        // Sets the political party (column at index 4) to be the variable that should
        // be predicted
        data.setClassIndex(data.numAttributes() - 1);

        return data;
    }

    // Creates a Naive Bayes model and trains it
    public static NaiveBayes train(Instances data) throws Exception {

        NaiveBayes model = new NaiveBayes();
        model.buildClassifier(data);
        return model;
    }

    // Predicts users political party
    public static double predictParty(Instances data, NaiveBayes model, int answer1, int answer2, int answer3,
            int answer4, int answer5) throws Exception {

        // Explains what the headers of the columns are
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("answer1"));
        attributes.add(new Attribute("answer2"));
        attributes.add(new Attribute("answer3"));
        attributes.add(new Attribute("answer4"));
        attributes.add(new Attribute("answer5"));

        // Assigns values to each of the columns
        DenseInstance newInstance = new DenseInstance(5);
        newInstance.setValue(0, answer1);
        newInstance.setValue(1, answer2);
        newInstance.setValue(2, answer3);
        newInstance.setValue(3, answer4);
        newInstance.setValue(4, answer5);
        newInstance.setDataset(data); // Uses the same dataset structure as the model that was used during training

        // Predict the political party
        double prediction = model.classifyInstance(newInstance);
        return prediction;
    }

    // Asks user for their actual political party
    public static String getActualParty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(
                "What is your actual political party?\n1: Liberal\n2: Conservative\n3: Moderate\n4: Libertarian\n");
        int party = scanner.nextInt();

        String actualParty = "";

        switch (party) {
            case 1:
                actualParty = "Liberal";
                break;
            case 2:
                actualParty = "Conservative";
                break;
            case 3:
                actualParty = "Moderate";
                break;
            case 4:
                actualParty = "Libertarian";
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.print("Thank you! Your answers will help me make future predictions!\n");
        return actualParty;
    }

    // Adds user answers and political party to CSV file
    public static void appendToCSV(int answer1, int answer2, int answer3, int answer4, int answer5, String actualParty) {
        String newEntry = (answer1 + "," + answer2 + "," + answer3 + "," + answer4 + "," + answer5 + "," + actualParty + "\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("survey_data.csv", true))) {
            writer.write(newEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
