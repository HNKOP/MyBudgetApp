using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BudgetBackend.Models
{
    public class Budget
    {
        public int Id { get; set; }
        public int Owner_Id { get; set; }
        public string Name { get; set; }
        public double Count { get; set; }
}
}