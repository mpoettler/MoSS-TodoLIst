using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using ToDoListAppBackend.Dtos;
using ToDoListAppBackend.Services;

namespace ToDoListAppBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ToDoItemController : ControllerBase
    {
        private readonly IToDoItemService _toDoItemService;

        public ToDoItemController(IToDoItemService toDoItemService)
        {
            _toDoItemService = toDoItemService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ToDoItemDto>>> GetToDoItems()
        {
            var toDoItems = await _toDoItemService.GetToDoItemsAsync();
            return Ok(toDoItems);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<ToDoItemDto>> GetToDoItem(int id)
        {
            var toDoItem = await _toDoItemService.GetToDoItemByIdAsync(id);
            if (toDoItem == null)
            {
                return NotFound();
            }
            return Ok(toDoItem);
        }

        [HttpPost]
        public async Task<ActionResult<ToDoItemDto>> CreateToDoItem(ToDoItemDto toDoItemDto)
        {
            await _toDoItemService.CreateToDoItemAsync(toDoItemDto);
            return CreatedAtAction(nameof(GetToDoItem), new { id = toDoItemDto.Id }, toDoItemDto);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateToDoItem(int id, ToDoItemDto toDoItemDto)
        {
            if (id != toDoItemDto.Id)
            {
                return BadRequest();
            }
            await _toDoItemService.UpdateToDoItemAsync(toDoItemDto);
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteToDoItem(int id)
        {
            await _toDoItemService.DeleteToDoItemAsync(id);
            return NoContent();
        }
    }
}
