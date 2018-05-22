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
    public class ValuesController : ApiController
    {
        BudgetContext db = new BudgetContext();
        // GET api/values
        public IEnumerable<string> Get()
        {
            User user = db.Users.Find(1);
            return new string[] { "Добро пожаловать", "Ответ с сервера!", user.Login , user.Password };
        }

        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
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
                if(us.Login == newuser.Login && us.Password == newuser.Password)
                {
                    jObject = new JObject();
                    jObject.Add("msg","OK");
                    jObject.Add("status","1");
                    return JsonConvert.SerializeObject(jObject);
                 //   return jObject.ToString();
                }
            }
            jObject = new JObject();
            jObject.Add("msg", "NO");
            jObject.Add("status", "0");
            return jObject.ToString();
            

        }

        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        public void Delete(int id)
        {
        }
    }
}
