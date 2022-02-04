package com.github.shoothzj.game2048.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private final MutableLiveData<Integer> score;

    public GameViewModel() {
        this.score = new MutableLiveData<>(0);
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score.postValue(score);
    }

    public void addScore(Integer score) {
        this.score.postValue(this.score.getValue() + score);
    }

}
