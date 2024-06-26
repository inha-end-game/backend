package com.inha.endgame.user;

import com.inha.endgame.room.RoomUser;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Getter
public class User {
	private final String username;
	private final String nickname;
	private String sessionId;
	private boolean isNew;
	private Date enterAt;
	private Date lastOverPacketSendAt;

	public User(String sessionId, String username, String nickname) {
		this.sessionId = sessionId;
		this.username = username;
		this.nickname = nickname;
		this.isNew = true;
	}

	public void syncRoom() {
		if(!isNew)
			throw new IllegalStateException("입장 가능한 상태의 유저가 아닙니다.");
		isNew = false;
		enterAt = new Date();
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setLastOverPacketSendAt(Date lastOverPacketSendAt) {
		this.lastOverPacketSendAt = lastOverPacketSendAt;
	}

	public RoomUser toRoomUser() {
		return new RoomUser(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof User)) {
			return false;
		}

		return Objects.equals(username, ((User) obj).getUsername());
	}

	// This is naive implementation of hashCode() that just defers to username,
	// but will work for our purposes
	@Override
	public int hashCode() {
		return Objects.hashCode(username);
	}
}
