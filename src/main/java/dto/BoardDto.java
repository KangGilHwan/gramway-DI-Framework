package dto;

import model.Board;

public class BoardDto {

    private Long id;
    private String userId;
    private String name;
    private String contents;

    public BoardDto() {
    }

    public BoardDto(Long id, String userId, String contents, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.contents = contents;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Board toBoard() {
        return new Board(id, userId, contents, name);
    }
}
