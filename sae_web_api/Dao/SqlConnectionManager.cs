using Npgsql;

namespace sae_web_api.Dao
{
    public static class SqlConnectionManager
    {
        public static NpgsqlConnection GetConnection()
        {
            var connection = new NpgsqlConnection()
            {
                ConnectionString = "Host=localhost;Username=postgres;Password=root;Database=sae;SearchPath=sae"
            };
            connection.Open();
            return connection;
        }
    }
}
