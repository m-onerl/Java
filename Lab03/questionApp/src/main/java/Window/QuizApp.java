package Window;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

public class QuizApp extends JFrame {
    private JLabel questionLabel;
    private JTextField answerField;
    private JButton verifyButton, nextQuestionButton, changeLanguageButton;
    private JLabel verificationLabel;
    private String correctAnswer;
    private String currentBookTitle;
    private ResourceBundle messages;
    private Locale currentLocale;
    private QuestionType currentQuestionType;


    private ImageIcon createIcon(String path) {
        try {
            URL imgURL = getClass().getResource("/icons/" + path);
            if (imgURL != null) {
                Image img = ImageIO.read(imgURL);
                Image resizedImg = img.getScaledInstance(24, 16, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImg);
            } else {
                System.err.println("Cannot find a file:" + path);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public QuizApp() {
        setTitle("Quiz");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        currentLocale = new Locale("en", "US");
        System.out.println("Current Locale: " + currentLocale);
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        System.out.println("Loaded messages for: " + messages.getLocale().toString());
        initUI();
        fetchBookTitle();
    }

    private void initUI() {
        getContentPane().setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        changeLanguageButton = new JButton();
        changeLanguageButton.setIcon(createIcon("us_flag.png"));
        headerPanel.add(changeLanguageButton);

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel(messages.getString("question"));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(questionLabel);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1));
        mainPanel.add(optionsPanel);

        answerField = new JTextField(20);
        answerField.setMaximumSize(answerField.getPreferredSize());
        mainPanel.add(answerField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());


        verifyButton = new JButton(messages.getString("verify"));
        nextQuestionButton = new JButton(messages.getString("nextQuestion"));

        buttonPanel.add(verifyButton);
        buttonPanel.add(nextQuestionButton);
        mainPanel.add(buttonPanel);

        verificationLabel = new JLabel(" ");
        verificationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(verificationLabel);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setupListeners();
    }


    private enum QuestionType {
        AUTHOR, COAUTHORS, MULTIPLE_CHOICE
    }


    private void selectQuestionType() {
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type) {
            case 0:
                currentQuestionType = QuestionType.AUTHOR;
                break;
            case 1:
                currentQuestionType = QuestionType.COAUTHORS;
                break;
            case 2:
                currentQuestionType = QuestionType.MULTIPLE_CHOICE;
                break;
        }
    }

    private void setupListeners() {
        verifyButton.addActionListener((ActionEvent e) -> verifyAnswer(answerField.getText()));
        nextQuestionButton.addActionListener((ActionEvent e) -> {
            fetchBookTitle();
            answerField.setText("");
            verificationLabel.setText(" ");
        });
        changeLanguageButton.addActionListener((ActionEvent e) -> toggleLanguage());
    }

    private void fetchBookTitle() {
        selectQuestionType();
        String[] keywords = {"love", "war", "magic", "adventure", "history", "science", "fantasy", "mystery"};
        Random random = new Random();
        String keyword = keywords[random.nextInt(keywords.length)];
        try {
            URL url = new URL("https://openlibrary.org/search.json?q=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8.name()) + "&fields=title,author_name&limit=8");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            int totalBooks = jsonResponse.getAsJsonArray("docs").size();
            if (totalBooks == 0) {
                SwingUtilities.invokeLater(() -> questionLabel.setText("No books found for keyword: " + keyword));
                return;
            }

            int bookIndex = random.nextInt(totalBooks);
            JsonObject selectedBook = jsonResponse.getAsJsonArray("docs").get(bookIndex).getAsJsonObject();

            currentBookTitle = selectedBook.get("title").getAsString();

            SwingUtilities.invokeLater(() -> {
                String formattedQuestion;
                if (currentQuestionType == QuestionType.AUTHOR) {

                    formattedQuestion = MessageFormat.format(messages.getString("questionAuthor"), currentBookTitle);
                    correctAnswer = selectedBook.getAsJsonArray("author_name").get(0).getAsString();
                } else {

                    formattedQuestion = MessageFormat.format(messages.getString("questionCoAuthors"), currentBookTitle);
                    correctAnswer = String.valueOf(selectedBook.getAsJsonArray("author_name").size() - 1);
                }
                questionLabel.setText(formattedQuestion);
                updateTexts();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void verifyAnswer(String userAnswer) {
        String message;
        if (currentQuestionType == QuestionType.AUTHOR) {
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                message = MessageFormat.format(messages.getString("correctAnswerAuthor"), currentBookTitle, correctAnswer);
            } else {
                System.out.println(correctAnswer);
                message = MessageFormat.format(messages.getString("wrongAnswerAuthor"), correctAnswer);
                System.out.println(message);
            }
        } else {
            try {
                int answerInt = Integer.parseInt(userAnswer);
                if (answerInt == Integer.parseInt(correctAnswer)) {
                    message = MessageFormat.format(messages.getString("correctAnswerCoAuthors"), currentBookTitle, correctAnswer);
                } else {
                    message = MessageFormat.format(messages.getString("wrongAnswerCoAuthors"), correctAnswer);
                }
            } catch (NumberFormatException e) {
                message = messages.getString("invalidInput");
            }
        }

        String finalMessage = message;
        SwingUtilities.invokeLater(() -> verificationLabel.setText(finalMessage));
    }





    private void toggleLanguage() {
        if (currentLocale.getLanguage().equals("en")) {
            currentLocale = new Locale("pl", "PL");
            changeLanguageButton.setIcon(createIcon("pl_flag.png"));
        } else {
            currentLocale = new Locale("en", "US");
            changeLanguageButton.setIcon(createIcon("us_flag.png"));
        }
        ResourceBundle.clearCache();
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        updateTexts();
    }

    private void updateTexts() {
        if (currentBookTitle != null && !currentBookTitle.isEmpty()) {
            String formattedQuestion;
            if (currentQuestionType == QuestionType.AUTHOR) {
                formattedQuestion = MessageFormat.format(messages.getString("questionAuthor"), currentBookTitle);
            } else {
                formattedQuestion = MessageFormat.format(messages.getString("questionCoAuthors"), currentBookTitle);
            }
            questionLabel.setText(formattedQuestion);
        }

        verifyButton.setText(messages.getString("verify"));
        nextQuestionButton.setText(messages.getString("nextQuestion"));

        verificationLabel.setText(" ");
    }
    }


