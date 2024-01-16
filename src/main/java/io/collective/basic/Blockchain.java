package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> blocks;

    public Blockchain() {
        this.blocks = new ArrayList<>();
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public void add(Block block) {
        blocks.add(block);
    }

    public int size() {
        return blocks.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        if (isEmpty()) {
            return true;
        }

        if (size() == 1) {
            return isMined(blocks.get(0));
        }

        for (int i = 1; i < size(); i++) {
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash()) ||
                    !isMined(currentBlock) ||
                    !currentBlock.calculatedHash().equals(currentBlock.getHash())) {
                return false;
            }
        }

        return true;
    }

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }

        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}
