using System.Collections.Generic;
using System.Threading.Tasks;
using ToDoListAppBackend.Dtos;
using ToDoListAppBackend.Models;
using ToDoListAppBackend.Repositories;

namespace ToDoListAppBackend.Services
{
    public class ToDoItemService : IToDoItemService
    {
        private readonly IToDoItemRepository _toDoItemRepository;

        public ToDoItemService(IToDoItemRepository toDoItemRepository)
        {
            _toDoItemRepository = toDoItemRepository;
        }

        public async Task<IEnumerable<ToDoItemDto>> GetToDoItemsAsync()
        {
            var toDoItems = await _toDoItemRepository.GetToDoItemsAsync();
            var toDoItemDtos = new List<ToDoItemDto>();

            foreach (var toDoItem in toDoItems)
            {
                toDoItemDtos.Add(new ToDoItemDto
                {
                    Id = toDoItem.Id,
                    Title = toDoItem.Title,
                    Description = toDoItem.Description,
                    Deadline = toDoItem.Deadline,
                    IsCompleted = toDoItem.IsCompleted
                });
            }

            return toDoItemDtos;
        }

        public async Task<ToDoItemDto> GetToDoItemByIdAsync(int id)
        {
            var toDoItem = await _toDoItemRepository.GetToDoItemByIdAsync(id);

            if (toDoItem == null)
            {
                return null;
            }

            return new ToDoItemDto
            {
                Id = toDoItem.Id,
                Title = toDoItem.Title,
                Description = toDoItem.Description,
                Deadline = toDoItem.Deadline,
                IsCompleted = toDoItem.IsCompleted
            };
        }

        public async Task CreateToDoItemAsync(ToDoItemDto toDoItemDto)
        {
            var toDoItem = new ToDoItem
            {
                Title = toDoItemDto.Title,
                Description = toDoItemDto.Description,
                Deadline = toDoItemDto.Deadline,
                IsCompleted = toDoItemDto.IsCompleted
            };

            await _toDoItemRepository.CreateToDoItemAsync(toDoItem);
        }

        public async Task UpdateToDoItemAsync(ToDoItemDto toDoItemDto)
        {
            var toDoItem = new ToDoItem
            {
                Id = toDoItemDto.Id,
                Title = toDoItemDto.Title,
                Description = toDoItemDto.Description,
                Deadline = toDoItemDto.Deadline,
                IsCompleted = toDoItemDto.IsCompleted
            };

            await _toDoItemRepository.UpdateToDoItemAsync(toDoItem);
        }

        public async Task DeleteToDoItemAsync(int id)
        {
            await _toDoItemRepository.DeleteToDoItemAsync(id);
        }
    }
}
