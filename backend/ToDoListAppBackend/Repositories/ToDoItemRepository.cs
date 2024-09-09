using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using ToDoListAppBackend.Data;
using ToDoListAppBackend.Models;

namespace ToDoListAppBackend.Repositories
{
    public class ToDoItemRepository : IToDoItemRepository
    {
        private readonly AppDbContext _context;

        public ToDoItemRepository(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<ToDoItem>> GetToDoItemsAsync()
        {
            return await _context.Tasks.ToListAsync();
        }

        public async Task<ToDoItem> GetToDoItemByIdAsync(int id)
        {
            return await _context.Tasks.FindAsync(id);
        }

        public async Task CreateToDoItemAsync(ToDoItem toDoItem)
        {
            _context.Tasks.Add(toDoItem);
            await _context.SaveChangesAsync();
        }

        public async Task UpdateToDoItemAsync(ToDoItem toDoItem)
        {
            _context.Entry(toDoItem).State = EntityState.Modified;
            await _context.SaveChangesAsync();
        }

        public async Task DeleteToDoItemAsync(int id)
        {
            var toDoItem = await _context.Tasks.FindAsync(id);
            if (toDoItem != null)
            {
                _context.Tasks.Remove(toDoItem);
                await _context.SaveChangesAsync();
            }
        }
    }
}
