import "./ToDoTemplate.scss";

function TodoTemplate({children}) {
    return (
        <div className="TodoTemplate">
            <div className="app-title">현재 재고량</div>
            <div className="content">{children}</div>
        </div>
    )
}

export default TodoTemplate;