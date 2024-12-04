package idi135;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
public class PacMan extends JPanel implements ActionListener, KeyListener {

    // Constants for board dimensions
    private final int rowCount = 21;
    private final int columnCount = 19;
    private final int tileSize = 32;
    private final int boardWidth = columnCount * tileSize;
    private final int boardHeight = rowCount * tileSize;

    // Game state variables
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    // Images for game elements
    private Image wallImage, blueGhostImage, orangeGhostImage, pinkGhostImage, redGhostImage;
    private Image cherryImage, foodImage, pacmanUpImage, pacmanDownImage, pacmanLeftImage, pacmanRightImage;

    // Collections for game elements
    private HashSet<Block> walls;
    private HashSet<Block> foods;
    private HashSet<Block> ghosts;
    private Block pacman;

    // Directions and game loop
    private final char[] directions = {'U', 'D', 'L', 'R'};
    private final Random random = new Random();
    private Timer gameLoop;

    // Tile Map for the game
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX", 
        "X        X        X", 
        "X XX XXX X XXX XX X",
        "X                 X", 
        "X XX X XXXXX X XX X", 
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX", 
        "OOOX X       X XOOO", 
        "XXXX X XXrXX X XXXX",
        "O       bpo       O", 
        "XXXX X XXXXX X XXXX", 
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX", 
        "X        X        X", 
        "X XX XXX X XXX XX X",
        "X  X     P     X  X", 
        "XX X X XXXXX X X XX", 
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X", 
        "X                 X", 
        "XXXXXXXXXXXXXXXXXXX"
    };

    // Block class to represent walls, ghosts, foods, and PacMan
    class Block {
        int x, y, width, height;
        int startX, startY;
        int velocityX = 0, velocityY = 0;
        char direction = 'U';
        Image image;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            // Keeps PacMan moving in direction after hitting wall
            for (Block wall : walls) {
                if (collision(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -tileSize / 4;
            } else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = tileSize / 4;
            } else if (this.direction == 'L') {
                this.velocityX = -tileSize / 4;
                this.velocityY = 0;
            } else if (this.direction == 'R') {
                this.velocityX = tileSize / 4;
                this.velocityY = 0;
            }
        }

        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    // Constructor to initialize the game
    public PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        loadImages();
        loadMap();
        initializeGhosts();
        gameLoop = new Timer(50, this); // 20fps (1000/50)
        gameLoop.start();
        SoundManager.playSound("java-man/src/main/resources/pacman_beginning.wav");
        
    }

    // Load game element images
    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
        cherryImage = new ImageIcon(getClass().getResource("/cherry.png")).getImage();
        foodImage = new ImageIcon(getClass().getResource("/powerFood.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
    }

    // Load map and initialize elements
    private void loadMap() {
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                char tileChar = tileMap[r].charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;

                switch (tileChar) {
                    case 'X': // Wall
                        walls.add(new Block(wallImage, x, y, tileSize, tileSize));
                        break;
                    case 'b': // Blue Ghost
                        ghosts.add(new Block(blueGhostImage, x, y, tileSize, tileSize));
                        break;
                    case 'o': // Orange Ghost
                        ghosts.add(new Block(orangeGhostImage, x, y, tileSize, tileSize));
                        break;
                    case 'p': // Pink Ghost
                        ghosts.add(new Block(pinkGhostImage, x, y, tileSize, tileSize));
                        break;
                    case 'r': // Red Ghost
                        ghosts.add(new Block(redGhostImage, x, y, tileSize, tileSize));
                        break;
                    case 'P': // PacMan
                        pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                        break;
                    case ' ': // Food
                        foods.add(new Block(foodImage, x + 14, y + 14, 4, 4));
                        break;
                }
            }
        }
    }

    // Initialize ghost movements
    private void initializeGhosts() {
        for (Block ghost : ghosts) {
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    // Paint components to render the game elements
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw game elements on screen
    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Block wall : walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        for (Block food : foods) {
            g.drawImage(food.image, food.x, food.y, food.width, food.height, null);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver) {
            g.drawString("Game Over: " + score, tileSize / 2, tileSize / 2);
        } else if (foods.isEmpty()) {
            g.drawString("Winner: " + score, tileSize / 2, tileSize / 2);
        } else {
            g.drawString("Lives: " + lives + " Score: " + score, tileSize / 2, tileSize / 2);
        }
    }

    // Move PacMan and handle collisions
    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        if (pacman.x < 0) pacman.x = boardWidth - tileSize;
        if (pacman.x + pacman.width > boardWidth) pacman.x = 0;

        for (Block wall : walls) {
            if (collision(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        // Handle ghost collisions
        for (Block ghost : ghosts) {
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;

            if (collision(ghost, pacman)) {
                lives--;
                if (lives == 0) {
                    gameOver();
                    return;
                }
                SoundManager.playSound("java-man/src/main/resources/pacman_death.wav");
                resetPositions();
            }

            if (ghost.y == tileSize * 9 && ghost.direction != 'U' && ghost.direction != 'D') {
                ghost.updateDirection('U');
            }

            for (Block wall : walls) {
                if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= boardWidth) {
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        // Check food collisions
        Block foodEaten = null;
        for (Block food : foods) {
            if (collision(pacman, food)) {
                foodEaten = food;
                score += 10;
                SoundManager.playSound("java-man/src/main/resources/pacman_chomp.wav");
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty()) {
            loadMap();   
            resetPositions();
        }
    }

    // Collision detection between two blocks
    public boolean collision(Block a, Block b) {
        return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    // Reset positions of PacMan and ghosts
    public void resetPositions() {
        pacman.reset();
        pacman.velocityX = pacman.velocityY = 0;

        for (Block ghost : ghosts) {
            ghost.reset();
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    // Game Over logic
    public void gameOver() {
        gameOver = true;
        gameLoop.stop();

        String playerName = JOptionPane.showInputDialog("Enter your username:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player" + new Random().nextInt(1000);
        }

        if (playerName != null && !playerName.trim().isEmpty()) {
            FirebaseService.writeLeaderboard(playerName, score);
        }

        showLeaderboard();
    }

    // Show leaderboard
    private void showLeaderboard() {
        FirebaseService.getLeaderboard(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String leaderboardMessage = "Leaderboard:\n";
                List<PlayerScore> leaderboard = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlayerScore playerScore = snapshot.getValue(PlayerScore.class);
                    leaderboard.add(playerScore);
                }

                // Sort leaderboard by score (highest to lowest)
                leaderboard.sort((p1, p2) -> Integer.compare(p2.score, p1.score));

                // Build leaderboard message
                for (int i = 0; i < 5; i++) {
                    PlayerScore playerScore = leaderboard.get(i);
                    leaderboardMessage += (i + 1) + ". " + playerScore.name + " - " + playerScore.score + " points\n";
                }

                // Show leaderboard in dialog
                JOptionPane.showMessageDialog(PacMan.this, leaderboardMessage, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                JOptionPane.showMessageDialog(PacMan.this, "Error loading leaderboard.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }

        // Update PacMan's image based on direction
        switch (pacman.direction) {
            case 'U': pacman.image = pacmanUpImage; break;
            case 'D': pacman.image = pacmanDownImage; break;
            case 'L': pacman.image = pacmanLeftImage; break;
            case 'R': pacman.image = pacmanRightImage; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
