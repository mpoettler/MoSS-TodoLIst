using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using ToDoListAppBackend.Models;

namespace ToDoListAppBackend.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<ToDoItem> ToDoItems { get; set; }
        public DbSet<User> Users { get; set; }
    }
}