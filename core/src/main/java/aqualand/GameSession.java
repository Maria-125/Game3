package aqualand;

import aqualand.managers.MemoryManager;

public class GameSession {
    private int score = 0;
    private int lives = 3;
    private int coins = 0;
    private boolean isGameOver = false;

    public void addScore(int points) {
        this.score += points;
        System.out.println("+ " + points + " очков. Всего: " + score);
    }

    public void addCoins(int value) {
        this.coins += value;
        System.out.println("+ " + value + " монеток. Всего за игру: " + coins);
    }

    public void loseLife() {
        lives--;
        System.out.println("Жизней осталось: " + lives);
        if (lives <= 0) {
            isGameOver = true;
            saveResults();
            System.out.println("ИГРА ОКОНЧЕНА! Рекорд сохранён.");
        }
    }

    private void saveResults() {
        int best = MemoryManager.loadRecord();
        if (score > best) {
            MemoryManager.saveRecord(score);
            System.out.println("🏆 НОВЫЙ РЕКОРД! " + score + " (было " + best + ")");
        } else {
            System.out.println("Рекорд не побит. Текущий: " + best + ", набрано: " + score);
        }

        int totalCoins = MemoryManager.loadCoins() + coins;
        MemoryManager.saveCoins(totalCoins);
        System.out.println("💰 Всего монеток сохранено: " + totalCoins);
    }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getCoins() { return coins; }
    public boolean isGameOver() { return isGameOver; }
}
