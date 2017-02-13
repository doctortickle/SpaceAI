/*
 * Copyright (C) 2017 Dylan Russell
 * This game is a heavily modified version of Battlecode 2017. I give 
 * significant credit to the team at MIT for their work on Battlecode and for
 * inspiring this body of work. Please check out battlecode.org for more info.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package world;

import java.util.Random;

/**
 *
 * @author Dylan Russell
 * 
 * Class that generates a sequence of unique pseudorandom
 * positive integer IDs for actors.
 * 
 */

public class IDGenerator {

    /**
     * The size of groups of IDs to reserve at a time.
     */
    public static final int ID_BLOCK_SIZE = 4096;

    /**
     * The smallest ID possible
     */
    public static final int MIN_ID = 10000;

    /**
     * The block of reserved IDs we walk through.
     */
    private final int[] reservedIDs;

    /**
     * The random generator used to shuffle blocks.
     */
    private final Random random;

    /**
     * Where we are in the current block.
     */
    private int cursor;

    /**
     * The number at the start of the next block.
     */
    private int nextIDBlock;

    /**
     * Create a new generator.
     *
     * @param seed the random seed to use.
     */
    public IDGenerator(int seed) {
        this.random = new Random(seed);
        this.reservedIDs = new int[ID_BLOCK_SIZE];

        setStart(MIN_ID);
    }

    /**
     * @return a new ID
     */
    public int nextID() {
        int id = this.reservedIDs[this.cursor];
        this.cursor++;

        if (this.cursor == ID_BLOCK_SIZE) {
            allocateNextBlock();
        }
        return id;
    }

    /**
     * Reserve the next ID_BLOCK_SIZE ints after this.nextIDBlock,
     * shuffle them with fisher-yates,
     * and reset the cursor.
     */
    private void allocateNextBlock() {
        if (this.nextIDBlock < 0) {
            throw new RuntimeException("No more positive ints. What did you do?");
        }

        this.cursor = 0;

        for (int i = 0; i < ID_BLOCK_SIZE; i++) {
            this.reservedIDs[i] = this.nextIDBlock + i + 1;
        }

        // fisher-yates shuffle
        for (int i = ID_BLOCK_SIZE - 1; i > 0; i--) {
            int index = this.random.nextInt(i+1);
            // swap
            int a = this.reservedIDs[index];
            this.reservedIDs[index] = this.reservedIDs[i];
            this.reservedIDs[i] = a;
        }

        this.nextIDBlock += ID_BLOCK_SIZE;
    }

    /**
     * Resets the IDGenerator to start at the given ID.
     *
     * @param startingID The ID to start allocating from
     */
    public void setStart(int startingID) {
        this.nextIDBlock = startingID;
        allocateNextBlock();
    }
}
