using System.Collections.Generic;
using System.IO;
using System.Json;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using org.apache.pdfbox.pdmodel;
using org.apache.pdfbox.util;
using ParallelDots;
using SautinSoft;
using Console = System.Console;

namespace sae_web_api
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateWebHostBuilder(args).Build().Run();            
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseUrls("http://localhost:5000/", "http://192.168.43.244:5000/")
                .UseStartup<Startup>();
    }
}
