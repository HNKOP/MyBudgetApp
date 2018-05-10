using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace BudgetBackend.Models
{
    public class BudgetContext : DbContext
    {
        public DbSet<User> Users { get; set; }
    }
}