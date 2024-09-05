﻿using System.Collections.Generic;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ToDoListAppBackend.Data;
using ToDoListAppBackend.Models;

namespace ToDoListAppBackend.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TasksController : ControllerBase
    {
        private readonly AppDbContext _context;
        private readonly ILogger<TasksController> _logger;

        public TasksController(AppDbContext context, ILogger<TasksController> logger)
        {
            _context = context;
            _logger = logger;
        }

        // GET: api/Tasks
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ToDoItem>>> GetTasks()
        {
            return await _context.Tasks.ToListAsync();
        }

        // GET: api/Tasks/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ToDoItem>> GetTask(int id)
        {
            var taskItem = await _context.Tasks.FindAsync(id);

            if (taskItem == null)
            {
                return NotFound();
            }

            return taskItem;
        }

        // POST: api/Tasks
        [HttpPost]
        public async Task<ActionResult<ToDoItem>> PostTask(ToDoItem task)
        {
    
            _logger.LogInformation("Received JSON: {@task}", task);

            var jsonReceived = JsonSerializer.Serialize(task);
            _logger.LogInformation("Received JSON: {jsonReceived}", jsonReceived);


            _context.Tasks.Add(task);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetTask), new { id = task.Id }, task);
        }

        // PUT: api/Tasks/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTask(int id, ToDoItem task)
        {

            _logger.LogInformation("Received update request for Task ID: {id}", id);
            if (id != task.Id)
            {
                return BadRequest();
            }

            _context.Entry(task).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
                _logger.LogInformation("Task updated successfully in the database.");
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TaskExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // DELETE: api/Tasks/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTask(int id)
        {
            var task = await _context.Tasks.FindAsync(id);
            if (task == null)
            {
                return NotFound();
            }

            _context.Tasks.Remove(task);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool TaskExists(int id)
        {
            return _context.Tasks.Any(e => e.Id == id);
        }

        private IEnumerable<ToDoItem> dummyTest()
        {
            return Enumerable.Range(1, 5).Select(index => new ToDoItem
            {
                Id = 1,
                Title = "Test",
                Description = "auch test",
                Deadline = DateTime.Now,
                IsCompleted = false,
                ListId = 1,
                Priority = "High",
                ReminderTime = null
            })
            .ToArray();

        }
    }
}
