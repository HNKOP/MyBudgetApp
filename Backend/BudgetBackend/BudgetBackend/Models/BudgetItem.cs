using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BudgetBackend.Models
{
    public class BudgetItem
    {
        public int Id { get; set; }
        public int Budget_Id { get; set; }
        public String Name { get; set; }
        public double Value { get; set; }
        public bool Type { get; set; }
    }
}