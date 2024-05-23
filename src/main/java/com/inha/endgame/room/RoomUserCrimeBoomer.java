package com.inha.endgame.room;

import com.inha.endgame.core.excel.MapReader;
import com.inha.endgame.user.CrimeType;
import com.inha.endgame.user.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class RoomUserCrimeBoomer extends RoomUserCrime {
    public RoomUserCrimeBoomer(User user) {
        super(user);
    }

    public RoomUserCrimeBoomer(RoomUser roomUser) {
        super(roomUser);
    }

    @Override
    public void setMaxMissionPhase() {
        this.maxMissionPhase = 4;
    }

    @Override
    public void playMission(int missionPhase, rVector3D missionPos) {
        super.playMission(missionPhase, missionPos);

        // 최종 미션
        if(missionPhase == this.maxMissionPhase) {

        }
    }

    @Override
    public boolean clearMission(int missionPhase) {
        return super.clearMission(missionPhase);
    }


    @Override
    public CrimeType getCrimeType() {
        return CrimeType.BOOMER;
    }

    @Override
    public Map<Integer, rVector3D> createRandomMissionPos() {
        Map<Integer, rVector3D> result = new HashMap<>();
        AtomicInteger missionIdx = new AtomicInteger(1);

        // 직업 미션 먼저 - 1개
        List<rVector3D> randomCrimeMissions = MapReader.getRandomCrimeMissions(this.getCrimeType());
        randomCrimeMissions.forEach(mission -> result.put(missionIdx.getAndIncrement(), mission));

        // 공통 미션 - 3개(완료 안해도 됨)
        List<rVector3D> randomCommonMissions = MapReader.getRandomCommonMissions(3);
        randomCommonMissions.forEach(mission -> result.put(missionIdx.getAndIncrement(), mission));

        return result;
    }
}
