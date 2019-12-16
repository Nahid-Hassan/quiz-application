/**
 * This is simple Java Swing GUI based Quiz Application program.
 * This Quiz game has three stages 1-> 10 Question(Easy) 2-> 20 Question(Medium) 3-> 30 Question(Hard)
 * If you select the 10-Q(Easy) Button then the current frame is disable and two new frame is created
 * One of the frame is Question frame and other frame is help frame.
 * Each Question frame is divided by 3 JPanel. First Panel take Question. 2nd panel take first two button and
 * third panel take 2nd two button.
 * The help frame is divided by 2 panel. First panel show a message and the 2nd  panel take take 3 button with option
 * Change Question, Pass Question and 50-50 Chance option
 * You can change the question clicking by change question button, pass question by clicking pass question button and to
 * take a 50-50 chance you need to click 50-50 button
 *
 * Then after all if you click on the question option button then question frame and help frame is currently dispose
 * and two new frame is created instantly. One frame show your answer is correct or not and another frame is showing
 * the your current score.
 *
 * Continue..................................later added some more documentation.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.WindowEvent;

public class QuizApplication {
    public static void main(String[] args) {
        new Quiz(); // Anonymous object
    }
}

class Quiz extends JFrame {
    private ImageIcon ii;
    private JPanel buttonPanel, labelPanel,hbuttonPanel, hlabelPanel, qbPanel1, qbPanel2, imagePanel;
    private JButton button, button1, button2, button3, button4, hbutton1, hbutton2, hbutton3;
    private String[] all_lines;
    private int limit = 0, counter = 1, idx = 0, score = 0;
    private JFrame qFrame, hFrame, mFrame, sFrame;
    private String s, path;
    private Path smile, currentPath, thanks, think, sad, question;

    public Quiz() {
        super("Choose Difficulty");

        currentPath = Paths.get(System.getProperty("user.dir"));
        smile = Paths.get(currentPath.toString(), "smile.gif");
        thanks = Paths.get(currentPath.toString(), "thanks.gif");
        think = Paths.get(currentPath.toString(), "think.gif");
        question = Paths.get(currentPath.toString(), "Question.txt");
        sad = Paths.get(currentPath.toString(), "sad.gif");

        setLayout(new GridLayout(2, 1));
        setBounds(448, 282, 500, 150);

        labelPanel = new JPanel();
        labelPanel.add(new JLabel("How many questions would you like to answer?"));
        buttonPanel = new JPanel(new FlowLayout());

        ii = new ImageIcon(new ImageIcon(think.toString()).getImage().getScaledInstance(25, 20, Image.SCALE_DEFAULT));

        // call createButton function
        button1 = CreateButton("10(Easy)", ii, "Click this to answer 10 question");
        button2 = CreateButton("20(Medium)", ii, "Click this to answer 20 question");
        button3 = CreateButton("30(Hard)", ii, "Click this to answer 30 question");

        buttonPanel.add(button1); buttonPanel.add(button2); buttonPanel.add(button3);

        add(labelPanel);
        add(buttonPanel);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // call getData(null)
        getData();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button1) {
                    dispose();
                    limit = 10;
                    QuestionAndHelp();
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button2) {
                    dispose();
                    limit = 20;
                    QuestionAndHelp();
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button3) {
                    dispose();
                    limit = 30;
                    QuestionAndHelp();
                }
            }
        });
    }
    public JButton CreateButton(String label, ImageIcon ii, String toolTipText) {
        button = new JButton(label, ii);
        button.setToolTipText(toolTipText);
        button.setBorderPainted(false);

        ButtonHover(button);

        return button;
    }

    public void ButtonHover(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GREEN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIManager.getColor("control"));
            }
        });
    }

    public void QuestionAndHelp() {
        //Question frame portion
        s = "Question " + String.valueOf(counter);
        qFrame = new JFrame(s);
        qFrame.setBounds(157,235, 500, 200);
        qFrame.setLayout(new GridLayout(3,1));

        ii = new ImageIcon(new ImageIcon(think.toString()).getImage().getScaledInstance(25, 20, Image.SCALE_DEFAULT));

        labelPanel = new JPanel();

        labelPanel.add(new JLabel(all_lines[idx++]));

        button1 = CreateButton(all_lines[idx++], ii, "Select Option 1");
        button2 = CreateButton(all_lines[idx++], ii, "Select Option 2");
        button3 = CreateButton(all_lines[idx++], ii, "Select Option 3");
        button4 = CreateButton(all_lines[idx++], ii, "Select Option 4");

        qbPanel1 = new JPanel();
        qbPanel2 = new JPanel();

        qbPanel1.add(button1); qbPanel1.add(button2);
        qbPanel2.add(button3); qbPanel2.add(button4);

        qFrame.add(labelPanel);
        qFrame.add(qbPanel1); qFrame.add(qbPanel2);


        // Help frame portion
        s = "Help-lines";
        hFrame = new JFrame(s);
        hFrame.setBounds(740, 251, 500, 150);
        hFrame.setLayout(new GridLayout(2, 1));

        ii = new ImageIcon(new ImageIcon(think.toString()).getImage().getScaledInstance(25, 20, Image.SCALE_DEFAULT));

        hlabelPanel = new JPanel();
        hlabelPanel.add(new JLabel("You can use the following Help-Lines one time each" , SwingConstants.CENTER));

        hbutton1 = CreateButton("Change Question", ii, "Click this to change question");
        hbutton2 = CreateButton("Pass Question", ii, "Click this to pass question");
        hbutton3 = CreateButton("50-50", ii, "Click this to disable to two button");

        hbuttonPanel = new JPanel(new FlowLayout());
        hbuttonPanel.add(hbutton1); hbuttonPanel.add(hbutton2); hbuttonPanel.add(hbutton3);

        hFrame.add(hlabelPanel);
        hFrame.add(hbuttonPanel);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button1) {
                    counter++;
                    qFrame.dispose();
                    hFrame.dispose();

                    if(button1.getText().equals(all_lines[idx])) {
                        idx++;
                        MessageAndAnswer(true);
                    } else {
                        idx++;
                        MessageAndAnswer(false);
                    }

                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button2) {
                    counter++;
                    qFrame.dispose();
                    hFrame.dispose();

                    if(button2.getText().equals(all_lines[idx])) {
                        idx++;
                        MessageAndAnswer(true);
                    } else {
                        idx++;
                        MessageAndAnswer(false);
                    }
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button3) {
                    counter++;
                    qFrame.dispose();
                    hFrame.dispose();

                    if(button3.getText().equals(all_lines[idx])) {
                        idx++;
                        MessageAndAnswer(true);
                    } else {
                        idx++;
                        MessageAndAnswer(false);
                    }
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == button4) {
                    counter++;
                    qFrame.dispose();
                    hFrame.dispose();

                    if(button4.getText().equals(all_lines[idx])) {
                        idx++;
                        MessageAndAnswer(true);
                    } else {
                        idx++;
                        MessageAndAnswer(false);
                    }
                }
            }
        });

        hbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == hbutton1) {
                    hFrame.dispose();
                    qFrame.dispose();
                    idx++;
                    hbutton1.setEnabled(false);
                    hbutton2.setEnabled(false);
                    hbutton3.setEnabled(false);
                    QuestionAndHelp();
                }
            }
        });
        hbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == hbutton2) {
                    counter++;
                    hFrame.dispose();
                    qFrame.dispose();
                    hbutton2.setEnabled(false);
                    hbutton1.setEnabled(false);
                    hbutton2.setEnabled(false);
                    idx++;

                    if ( counter <= limit) {
                        QuestionAndHelp();
                    } else {
                        FinalResult();
                    }
                }
            }
        });
        hbutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == hbutton3) {
                    int inner = idx - 5;
                    String answer = all_lines[idx];

                    hbutton3.setEnabled(false);
                    hbutton1.setEnabled(false);
                    hbutton2.setEnabled(false);
                    int check = -1;
                    for(int i = inner; i < idx; i++) {
                        if(answer.equals(all_lines[i])) {
                            check = i;
                            break;
                        }
                    }
                    if((check - inner) % 4 == 1) {
                        button3.setEnabled(false);
                        button2.setEnabled(false);
                    } else if ((check - inner) % 4 == 2) {
                        button1.setEnabled(false);
                        button4.setEnabled(false);
                    } else if ((check - inner ) % 4 == 3) {
                        button1.setEnabled(false);
                        button2.setEnabled(false);
                    } else {
                        button2.setEnabled(false);
                        button3.setEnabled(false);
                    }
                }
            }
        });
        qFrame.pack();
        hFrame.setVisible(true);
        qFrame.setVisible(true);
    }

    public void MessageAndAnswer(boolean flag) {
        mFrame = new JFrame("Message");
        mFrame.setBounds(462, 212, 325,325);
        mFrame.setLayout(new GridLayout(3,1));

        sFrame = new JFrame("Score");
        sFrame.setBounds(853, 277, 200,200);
        sFrame.setLayout(new GridLayout(2, 1));

        labelPanel = new JPanel();
        if (flag) {
            score = score + 10;
            JPanel messageTrueContentPanel = new JPanel();
            JPanel messageTrueImagePanel = new JPanel();
            JPanel messageTrueButtonPanel = new JPanel(new FlowLayout());

            ii = new ImageIcon(new ImageIcon(smile.toString()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
            mFrame.add(messageTrueContentPanel.add(new JLabel("Correct Answer. You get 10 Marks.", SwingConstants.CENTER)));
            mFrame.add(messageTrueImagePanel.add(new JLabel(ii)));
            button = new JButton("Ok");
            messageTrueButtonPanel.add(button);
            mFrame.add(messageTrueButtonPanel);

            sFrame.add(new JLabel("Your Score is shown below\n", SwingConstants.CENTER));
            sFrame.add(new JLabel(String.valueOf(score), SwingConstants.CENTER));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                   if (actionEvent.getSource() == button) {
                        if(counter <= limit) {
                            sFrame.dispose();
                            mFrame.dispose();

                            QuestionAndHelp();
                        } else {
                            mFrame.dispose();
                            sFrame.dispose();
                            FinalResult();
                        }
                    }
                }
            });
            mFrame.setVisible(true);
            sFrame.setVisible(true);
        } else {
            score = score - 5;
            JPanel messageFalseContentPanel = new JPanel();
            JPanel messageFalseImagePanel = new JPanel();
            JPanel messageFalseButtonPanel = new JPanel(new FlowLayout());

            ii = new ImageIcon(new ImageIcon(sad.toString()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
            mFrame.add(messageFalseContentPanel.add(new JLabel("Wrong answer. You get -5 Marks.", SwingConstants.CENTER)));
            mFrame.add(messageFalseImagePanel.add(new JLabel(ii)));
            button = new JButton("Ok");
            messageFalseButtonPanel.add(button);
            mFrame.add(messageFalseButtonPanel);

            sFrame.add(new JLabel("Your Score is shown below\n", SwingConstants.CENTER));
            sFrame.add(new JLabel(String.valueOf(score), SwingConstants.CENTER));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(actionEvent.getSource() == button) {
                        if(counter <= limit) {
                            sFrame.dispose();
                            mFrame.dispose();

                            QuestionAndHelp();
                        } else {
                            mFrame.dispose();
                            sFrame.dispose();
                            FinalResult();
                        }
                    }
                }
            });
            mFrame.setVisible(true);
            sFrame.setVisible(true);
        }
    }

    public void FinalResult() {
        JFrame finalMessageFrame = new JFrame("Message");
        JFrame finalScoreFrame = new JFrame("Score");
        finalMessageFrame.setBounds(493, 199, 325, 325);
        finalScoreFrame.setBounds(851, 256, 300, 200);

        finalMessageFrame.setLayout(new GridLayout(3,1));
        JPanel finalMessageFrameContentPanel = new JPanel();
        JPanel finalMessageFrameImagePanel = new JPanel();
        JPanel finalMessageFrameButtonPanel = new JPanel(new FlowLayout());

        JLabel finalMessageFrameLabel = new JLabel("Thank you for playing the game.", SwingConstants.CENTER);
        finalMessageFrameContentPanel.add(finalMessageFrameLabel);
        finalMessageFrame.add(finalMessageFrameContentPanel);

        ii = new ImageIcon(new ImageIcon(thanks.toString()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        finalMessageFrameImagePanel.add(new JLabel(ii));
        finalMessageFrame.add(finalMessageFrameImagePanel);

        JButton finalMessageFrameButton = new JButton("Ok");
        finalMessageFrameButtonPanel.add(finalMessageFrameButton);
        finalMessageFrame.add(finalMessageFrameButtonPanel);

        finalMessageFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == finalMessageFrameButton) {
                    System.exit(0);
                }
            }
        });

        finalScoreFrame.setLayout(new GridLayout(2, 1));
        finalScoreFrame.add(new JLabel("Your score is shown below.", SwingConstants.CENTER));
        finalScoreFrame.add(new JLabel(String.valueOf(score), SwingConstants.CENTER));
        
        finalScoreFrame.setVisible(true);
        finalMessageFrame.setVisible(true);
    }

    public void getData() {
        all_lines = new String[300];
        try {
            File f = new File(question.toString());

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            int i = 0;
            while ((readLine = b.readLine()) != null) {
                all_lines[i] = readLine;
                i += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}