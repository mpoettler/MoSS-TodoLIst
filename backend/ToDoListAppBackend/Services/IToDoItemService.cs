using System.Collections.Generic;
using System.Threading.Tasks;
using ToDoListAppBackend.Dtos;

namespace ToDoListAppBackend.Services
{
    public interface IToDoItemService
    {
        Task<IEnumerable<ToDoItemDto>> GetToDoItemsAsync();
        Task<ToDoItemDto> GetToDoItemByIdAsync(int id);
        Task CreateToDoItemAsync(ToDoItemDto toDoItemDto);
        Task UpdateToDoItemAsync(ToDoItemDto toDoItemDto);
        Task DeleteToDoItemAsync(int id);
    }
}
