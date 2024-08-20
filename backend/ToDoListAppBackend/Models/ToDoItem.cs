using System.ComponentModel.DataAnnotations.Schema;

namespace ToDoListAppBackend.Models
{
    public class ToDoItem
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateTime? Deadline { get; set; }

        [Column("is_completed")]
        public bool IsCompleted { get; set; } 

        [Column("list_id")]
        public int ListId { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; }
    }
}
