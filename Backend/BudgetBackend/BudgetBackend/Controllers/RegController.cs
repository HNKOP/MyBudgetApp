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
    public class RegController : ApiController
    {
        BudgetContext db = new BudgetContext();
        [HttpPost]
        public string Post([FromBody]string value)
        {
            String result = value;
            JObject json = JObject.Parse(result);
            JObject jObject;
            User newuser = new User();
            newuser.Login = json["Login"].Value<String>();
            newuser.Password = json["Password"].Value<String>();
            var users = db.Users.ToList();
            foreach (var us in users)
            {
                if (us.Login == newuser.Login)
                {
                    jObject = new JObject();
                    jObject.Add("msg", "Данное имя пользователя уже существует");
                    jObject.Add("status", "0");
                    return JsonConvert.SerializeObject(jObject);
                    //   return jObject.ToString();
                }
            }
            db.Users.Add(newuser);
            db.SaveChanges();

            jObject = new JObject();
            jObject.Add("msg", "Пользователь " + newuser.Login + " зарегистрирован");
            jObject.Add("status", "1");
            return JsonConvert.SerializeObject(jObject);
        }
    }
}
