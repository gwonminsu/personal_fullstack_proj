import React, { useState, useEffect } from 'react';
import { fetchComments, addComment, updateComment, deleteComment } from '../services/commentService';
import sessionService from '../services/sessionService';

function Comment({ postId }) {
  // 상태 변수 정의
  const [comments, setComments] = useState([]); // 댓글 목록 상태
  const [newComment, setNewComment] = useState(''); // 새로운 댓글 입력 상태
  const [editStatus, setEditStatus] = useState({}); // 댓글 수정 상태
  const user = sessionService.getUser(); // 현재 사용자 정보
  
  // 게시물이 변경될 때마다 댓글을 가져오는 useEffect
  useEffect(() => {
    async function loadComments() {
      try {
        // 해당 게시물의 댓글을 가져와 상태 업데이트
        const fetchedComments = await fetchComments(postId);
        setComments(fetchedComments);
      } catch (error) {
        // 에러 발생 시 콘솔에 로그 출력
        console.error('Error fetching comments:', error);
      }
    }
    loadComments();
  }, [postId]);

  // 새로운 댓글 입력 핸들러
  const handleNewCommentChange = (e) => {
    setNewComment(e.target.value);
  };

  // 새로운 댓글 추가 핸들러
  const handleAddComment = async () => {
    try {
      // 새로운 댓글을 추가하고 상태 업데이트
      const addedComment = await addComment(postId, user.userId, newComment);
      setComments([...comments, addedComment]);
      setNewComment('');
    } catch (error) {
      // 에러 발생 시 콘솔에 로그 출력
      console.error('Error adding new comment:', error);
    }
  };

  // 댓글 수정 클릭 핸들러
  const handleEditClick = (commentId) => {
    setEditStatus({ ...editStatus, [commentId]: true });
  };

  // 댓글 수정 내용 변경 핸들러
  const handleEditChange = (commentId, content) => {
    // 수정된 댓글 내용을 상태에 반영
    setComments(comments.map((comment) =>
      comment.id === commentId ? { ...comment, content: content } : comment
    ));
  };

  // 댓글 수정 업데이트 핸들러
  const handleUpdate = async (commentId) => {
    try {
      // 수정된 댓글 내용을 가져와 업데이트하고 수정 상태 업데이트
      const content = comments.find(comment => comment.id === commentId).content;
      const updatedComment = await updateComment(commentId, content);
      setComments(comments.map(comment => 
        comment.id === commentId ? updatedComment : comment
      ));
      setEditStatus({ ...editStatus, [commentId]: false });
    } catch (error) {
      // 에러 발생 시 콘솔에 로그 출력
      console.error('Error updating comment:', error);
    }
  };

  // 댓글 삭제 핸들러
  const handleDelete = async (commentId) => {
    try {
      // 댓글 삭제하고 상태 업데이트
      await deleteComment(commentId);
      setComments(comments.filter(comment => comment.id !== commentId));
    } catch (error) {
      // 에러 발생 시 콘솔에 로그 출력
      console.error('Error deleting comment:', error);
    }
  };

  return (
    <div>
      <h3>댓글 ({comments.length}개)</h3>
      <div className=" left-0 w-11/12 mb-4 border-t border-gray-300"></div>
      {/* 댓글 목록 표시 */}
      {comments.map(comment => (
        <div key={comment.id} className="comment-bubble">
          <div className="mb-5">
            <div className='flex justify-between w-[69%]'>
                <span className="comment-author font-semibold">{comment.userName}</span>
                <span className="comment-date text-sm text-gray-500">
                    {new Date(comment.createdAt).toLocaleString()}
                </span>
            </div>
            {/* 수정 모드인 경우 입력 필드와 완료 버튼 표시 */}
            {editStatus[comment.id] ? (
              <>
                <div className='flex justify-start'>
                    <img src="/images/user_ico.png" alt="User" className="h-8 w-8 mr-2 mt-3" />
                    <div className='flex justify-between bg-white p-3 pb-1 rounded-xl shadow-lg w-2/3'>
                        <textarea
                            className="w-[96%] resize-y border rounded focus:outline-none focus:ring-2 focus:ring-purple-600"
                            value={comment.content}
                            onChange={(e) => handleEditChange(comment.id, e.target.value)}
                            rows="2">
                        </textarea>
                        <button onClick={() => handleUpdate(comment.id)}>✔️</button>
                    </div>
                    
                </div>
              </>
            ) : (
              <>
                {/* 수정 모드가 아닌 경우 댓글 내용과 수정 및 삭제 버튼 표시 */}
                <div className='flex justify-start'>
                    <img src="/images/user_ico.png" alt="User" className="h-8 w-8 mr-2 mt-3" />
                    <div className='flex justify-between bg-white p-3 pb-1 rounded-xl shadow-lg w-2/3'>
                        {comment.content}
                        {user && user.userId === comment.userId && (
                        <span className='flex items-center'>
                            <button onClick={() => handleEditClick(comment.id)}>✏️</button>
                            <button onClick={() => handleDelete(comment.id)}>🗑️</button>
                        </span>
                        )}
                    </div>
                </div>
              </>
            )}
          </div>
        </div>
      ))}
      {/* 새로운 댓글 입력 필드 */}
      <div className='flex justify-start items-center'>
        <textarea
            className="resize-none w-[90%] h-20 p-2 border rounded-md"
            value={newComment}
            onChange={handleNewCommentChange}
            placeholder="댓글을 입력하세요"
        />
        <button
            onClick={handleAddComment}
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded h-[4.8rem] ml-3"
        >
        등록
        </button>
      </div>
    </div>
  );
}

export default Comment;
