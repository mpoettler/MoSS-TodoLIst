using System.Collections.Generic;
using System.Threading.Tasks;
using ToDoListAppBackend.Models;

namespace ToDoListAppBackend.Repositories
{
    public interface IToDoItemRepository
    {
        Task<IEnumerable<ToDoItem>> GetToDoItemsAsync();
        Task<ToDoItem> GetToDoItemByIdAsync(int id);
        Task CreateToDoItemAsync(ToDoItem toDoItem);
        Task UpdateToDoItemAsync(ToDoItem toDoItem);
        Task DeleteToDoItemAsync(int id);
    }
}
