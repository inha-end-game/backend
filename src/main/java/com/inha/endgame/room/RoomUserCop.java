package com.inha.endgame.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inha.endgame.core.excel.JsonReader;
import com.inha.endgame.user.UserState;
import com.inha.endgame.user.AimState;
import com.inha.endgame.user.CopAttackState;
import com.inha.endgame.user.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class RoomUserCop extends RoomUser {
    @JsonIgnore
    private Date availShotAt = new Date();
    @JsonIgnore
    private Date stunAvailAt = new Date();
    @JsonIgnore
    private rVector3D targetAimPos = null;
    @JsonIgnore
    private String targetUsername = null;
    @JsonIgnore
    private CopAttackState copAttackState = CopAttackState.NONE;

    public RoomUserCop(User user) {
        super(user);
    }

    public RoomUserCop(String username, String nickname, rVector3D pos, rVector3D rot, RoomUserType roomUserType) {
        super(username, nickname, pos, rot, roomUserType);
    }

    public RoomUserCop(RoomUser roomUser) {
        super(roomUser.getUsername(), roomUser.getNickname(), roomUser.getPos(), roomUser.getRot(), roomUser.getRoomUserType());
    }

    public synchronized void aiming(rVector3D targetPos) {
        if(!this.copAttackState.equals(CopAttackState.STUN))
            this.copAttackState = CopAttackState.AIM;
        this.targetAimPos = targetPos;
    }

    public synchronized void endAimingAndStun() {
        this.copAttackState = CopAttackState.NONE;
        this.targetAimPos = null;
        this.targetUsername = null;
    }

    public synchronized void stun(RoomUser targetUser) {
        Date now = new Date();
        if(now.before(this.stunAvailAt))
            throw new IllegalStateException("아직 검문을 시도할 수 없습니다.");

        if(!this.copAttackState.equals(CopAttackState.AIM))
            throw new IllegalStateException("조준 상태에서만 검문할 수 있습니다.");

        if(!targetUser.getUserState().equals(UserState.NORMAL))
            throw new IllegalStateException("검문할 수 있는 상태가 아닙니다.");

        var aimRange = JsonReader._int(JsonReader.model("shot", "shot_rule", "AimRange"));
        if(targetUser.getPos().distance(this.getPos()) > aimRange)
            throw new IllegalStateException("거리가 너무 멀어 검문할 수 없습니다.");


        this.targetUsername = targetUser.getUsername();
        this.copAttackState = CopAttackState.STUN;

        var nextStunCoolTime = JsonReader._time(JsonReader.model("shot", "shot_rule", "InspectCoolTime"));
        this.stunAvailAt = new Date(now.getTime() + nextStunCoolTime);

        var reloadTime = JsonReader._time(JsonReader.model("shot", "shot_rule", "ReloadTime"));
        this.availShotAt = new Date(now.getTime() + reloadTime);
    }

    public void checkShot(RoomUser targetUser) {
        Date now = new Date();
        if(now.before(this.availShotAt))
            throw new IllegalStateException("아직 사격할 수 없습니다.");

        if(!this.targetUsername.equals(targetUser.getUsername()))
            throw new IllegalStateException("타겟이 없습니다.");

        if(!this.copAttackState.equals(CopAttackState.STUN) && targetUser.getUserState().equals(UserState.STUN))
            throw new IllegalStateException("검문 상태에서만 사용할 수 있습니다.");
    }
}