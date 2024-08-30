package com.web.demo1.service;

import com.web.demo1.dto.board.request.BoardSaveRequestDto;
import com.web.demo1.dto.board.request.BoardUpdateRequestDto;
import com.web.demo1.dto.board.response.BoardSaveResponseDto;
import com.web.demo1.dto.board.response.BoardSimpleResponseDto;
import com.web.demo1.dto.board.response.BoardUpdateResponseDto;
import com.web.demo1.entity.Board;
import com.web.demo1.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        Board newboard = new Board(boardSaveRequestDto.getTitle(), boardSaveRequestDto.getContents());

        Board saveBoard = boardRepository.save(newboard);

        return new BoardSaveResponseDto(saveBoard.getId(), saveBoard.getTitle(), saveBoard.getContents());
    }

    public Page<BoardSimpleResponseDto> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size);

        Page<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(pageable);

        return boards.map(board -> new BoardSimpleResponseDto(
                board.getId(),
                board.getTitle(),
                board.getComments()
        ));
    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("보드 없어요"));
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContents());

        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContents());
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        if(!boardRepository.existsById(boardId)) {
            throw new NullPointerException("보드 업성요");
        }

        boardRepository.deleteById(boardId);
    }
}
