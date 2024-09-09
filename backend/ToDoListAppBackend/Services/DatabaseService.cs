namespace ToDoListAppBackend.Services
{
    using MySql.Data.MySqlClient;

    public class DatabaseService
    {
        private string connectionString = "Server=localhost;Database=todo_app_db;User Id=root;Password=your_password;";

        public void Connect()
        {
            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                conn.Open();
                // Hier kannst du SQL-Befehle ausführen
                Console.WriteLine("Verbindung erfolgreich hergestellt.");
            }
        }
    }

}
