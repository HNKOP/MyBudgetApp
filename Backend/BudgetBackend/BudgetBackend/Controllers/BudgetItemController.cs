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
    public class BudgetItemController : ApiController
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

                    BudgetItem budgetitem = new BudgetItem();
                    budgetitem.Name = json["Name"].Value<String>();
                    budgetitem.Budget_Id = json["Budget_Id"].Value<int>();
                    budgetitem.Type = json["Type"].Value<bool>();
                    budgetitem.Value = json["Value"].Value<double>();
                    db.BudgetItems.Add(budgetitem);

                    var budgets = db.Budgets.ToList();
                    for(int i = 0; i < budgets.Count; i++)
                    {
                        if(budgets[i].Id == budgetitem.Budget_Id)
                        {
                            if(budgetitem.Type)
                            {
                                budgets[i].Count += budgetitem.Value;
                            }
                            else
                            {
                                budgets[i].Count -= budgetitem.Value;
                            }
                            
                        }
                    }

                    db.SaveChanges();
                    jObject = new JObject();
                    jObject.Add("msg", "Пункт добавлен, счет обновлен");
                    jObject.Add("status", "1");
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
