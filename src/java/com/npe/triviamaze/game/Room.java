package com.npe.triviamaze.game;

import java.util.Deque;

import com.npe.triviamaze.game.triviaitem.TriviaItem;

public class Room
{
    private Door up;
    private Door right;
    private Door down;
    private Door left;

    Room()
    {
        // all doors do not exist
    }

    Room(Room up, Room right, Room down, Room left, Deque<TriviaItem> questionDeque)
    {
        this.up = (up == null) ? new Door(questionDeque) : up.getDown();
        this.right = (right == null) ? new Door(questionDeque) : right.getLeft();
        this.down = (down == null) ? new Door(questionDeque) : down.getUp();
        this.left = (left == null) ? new Door(questionDeque) : left.getRight();
    }

    protected Door getUp()
    {
        return up;
    }

    protected Door getRight()
    {
        return right;
    }

    protected Door getDown()
    {
        return down;
    }

    protected Door getLeft()
    {
        return left;
    }

    protected Door getDoor(Direction direction)
    {
        if(direction == Direction.Up)
            return up;
        if(direction == Direction.Down)
            return down;
        if(direction == Direction.Left)
            return left;
        else
            return right;
    }

    public boolean isDoorLocked(Direction direction)
    {
        if(direction == Direction.Up)
            return up == null ? false : up.isLocked();
        if(direction == Direction.Down)
            return down == null ? false : down.isLocked();
        if(direction == Direction.Left)
            return left == null ? false : left.isLocked();
        else
            return right == null ? false : right.isLocked();
    }

    public boolean isDoorTraversable(Direction direction)
    {
        if(direction == Direction.Up)
            return up == null ? false : !up.isLocked();
        if(direction == Direction.Down)
            return down == null ? false : !down.isLocked();
        if(direction == Direction.Left)
            return left == null ? false : !left.isLocked();
        else
            return right == null ? false : !right.isLocked();
    }

    public boolean isDoorOpen(Direction direction)
    {
        if(direction == Direction.Up)
            return up == null ? false : up.isOpen();
        if(direction == Direction.Down)
            return down == null ? false : down.isOpen();
        if(direction == Direction.Left)
            return left == null ? false : left.isOpen();
        else
            return right == null ? false : right.isOpen();
    }

    protected String[] getQuestion(Direction to)
    {
        if(to == Direction.Up)
            return up == null ? null : up.getQuestion();
        if(to == Direction.Down)
            return down == null ? null : down.getQuestion();
        if(to == Direction.Left)
            return left == null ? null : left.getQuestion();
        else
            return right == null ? null : right.getQuestion();
    }

    protected void answerQuestion(Direction to, String answer)
    {
        if(to == Direction.Up)
        {
            if(up != null)
                up.answerQuestion(answer);
        }
        else if(to == Direction.Down)
        {
            if(down != null)
                down.answerQuestion(answer);
        }
        else if(to == Direction.Left)
        {
            if(left != null)
                left.answerQuestion(answer);
        }
        else
        {
            if(right != null)
                right.answerQuestion(answer);
        }
    }
}
