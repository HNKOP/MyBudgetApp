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
    public class GetBudgetItemsByBudgetIdController : ApiController
    {
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


                    int budget_id = json["Budget_Id"].Value<int>();

                    var budgetitems = db.BudgetItems.ToList();
                    List<BudgetItem> budgetslist = new List<BudgetItem>();

                    foreach (var us in budgetitems)
                    {
                        if (us.Budget_Id == budget_id)
                        {
                            budgetslist.Add(us);

                        }
                    }

                    //jObject = new JObject();
                    // jObject.Add("msg", "Возвращено " + budgetslist.Count.ToString());
                    // jObject.Add("status", "1");
                    // jObject.Add("list", JsonConvert.SerializeObject(budgetslist));
                    return JsonConvert.SerializeObject(budgetslist);
                    return JsonConvert.SerializeObject(jObject);
                }
                catch (Exception e)
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
