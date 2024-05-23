package com.inha.endgame.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

public class GameOverInfo {
    public enum OverJob {
        COP,
        SPY,
        BOOMER,
        ASSASSIN,
    }
    public enum OverType {
        TRUE,
        NORMAL,
        BAD,
    }

    private OverJob job;
    private String username;
    private String nickname;
    private OverType overType;

    private List<Date> missionClearAt;

    private String title;
    private String description;

    public GameOverInfo(OverJob job, String username, String nickname, List<Date> missionClearAt) {
        this.job = job;
        this.nickname = nickname;
        this.username = username;
        this.missionClearAt = missionClearAt;
    }

    public void putDetail(OverType overType, String title, String description) {
        this.overType = overType;
        this.title = title;
        this.description = description;
    }
}
