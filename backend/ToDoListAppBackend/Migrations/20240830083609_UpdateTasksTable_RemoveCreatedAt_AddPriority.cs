using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace ToDoListAppBackend.Migrations
{
    /// <inheritdoc />
    public partial class UpdateTasksTable_RemoveCreatedAt_AddPriority : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            // Entfernen der `created_at`-Spalte
            migrationBuilder.DropColumn(
                name: "created_at",
                table: "Tasks");

            // Entfernen der `repeat_interval`-Spalte
            migrationBuilder.DropColumn(
                name: "repeat_interval",
                table: "Tasks");

            // Hinzufügen der neuen `Priority`-Spalte
            migrationBuilder.AddColumn<string>(
                name: "Priority",
                table: "Tasks",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            // Hinzufügen der `created_at`-Spalte (falls Rollback)
            migrationBuilder.AddColumn<DateTime>(
                name: "created_at",
                table: "Tasks",
                nullable: false,
                defaultValue: new DateTime(0));

            // Hinzufügen der `repeat_interval`-Spalte (falls Rollback)
            migrationBuilder.AddColumn<string>(
                name: "repeat_interval",
                table: "Tasks",
                nullable: true);

            // Entfernen der `Priority`-Spalte (falls Rollback)
            migrationBuilder.DropColumn(
                name: "Priority",
                table: "Tasks");
        }
    }
}
