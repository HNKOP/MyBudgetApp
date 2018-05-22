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
    public class GetUserIdController : ApiController
    {
        BudgetContext db = new BudgetContext();
        

        [HttpPost]
        public string Post([FromBody]string value)
        {
            String result = value;
            JObject json = JObject.Parse(result);
            JObject jObject;

            var users = db.Users.ToList();
            foreach (var us in users)
            {
                if (us.Login == json["Login"].Value<String>())
                {
                    jObject = new JObject();
                    jObject.Add("msg", "Возвращение ID пользователя " + us.Login);
                    jObject.Add("status", "1");
                    jObject.Add("id", us.Id);
                    return JsonConvert.SerializeObject(jObject);
                    
                }
            }
            jObject = new JObject();
            jObject.Add("msg", "Пользователь не найден");
            jObject.Add("status", "0");
            jObject.Add("id", -1);
            return JsonConvert.SerializeObject(jObject);
        }

    }
}
