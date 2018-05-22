using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BudgetBackend.Models
{
    public class BudgetToUser
    {
        public int Id { get; set; }
        public int Budget_Id { get; set; }
        public int User_Id { get; set; }
    }
}