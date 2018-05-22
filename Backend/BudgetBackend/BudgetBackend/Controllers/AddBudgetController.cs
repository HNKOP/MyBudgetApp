using BudgetBackend.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace BudgetBackend.Controllers
{
    public class AddBudgetController : ApiController
    {
        //api/addbudget
        [HttpPost]
        public string Post([FromBody]string value)
        {
            using (BudgetContext db = new BudgetContext())
            {
                JObject jObject;
                try
                {
                    String result = value;
                    JObject json = JObject.Parse(result);
                    
                    Budget budget = new Budget();
                    budget.Name = json["Name"].Value<String>();
                    budget.Owner_Id = json["Owner_Id"].Value<int>();
                    budget.Count = 0;
                    db.Budgets.Add(budget);
                    db.SaveChanges();
                    jObject = new JObject();
                    jObject.Add("msg", "Счет создан");
                    jObject.Add("status", "1");
                    return JsonConvert.SerializeObject(jObject);
                }
                catch(Exception e)
                {
                    jObject = new JObject();
                    jObject.Add("msg", e.Message);
                    jObject.Add("status", "0");
                    return JsonConvert.SerializeObject(jObject);
                }
                
            }
        }
    }
}
