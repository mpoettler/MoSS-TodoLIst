using ToDoListAppBackend.Models;

namespace ToDoListAppBackend.Dtos
{
    public class ToDoItemDto
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateTime Deadline { get; set; }
        public bool isCompleted { get; set; } // Ensure the case matches
        public int ListId { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
