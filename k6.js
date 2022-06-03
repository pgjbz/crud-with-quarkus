import http from "k6/http";
import { check } from 'k6';

const baseUrl = 'http://localhost:8080';

export const options = {
    stages: [
      { duration: '10s', target: 10, env: 'TYPE' },
      { duration: '10s', target: 100 },
      { duration: '10s', target: 500 },
      { duration: '10s', target: 0 },
    ],
  };

const requests = {
    "get" : url => http.get(url, baseUrl),
    "post": url => {
        const props = {
            headers: {
                'Content-Type': 'application/json'
            }
        };
        return http.post(url, '{"name": "Vassoura", "price": 10.99}', props);
    }
}

export default function() {
    const envVar = __ENV.TYPE;
    const req = envVar ? envVar.toLowerCase() : 'get';
    const productsUrl = `${baseUrl}/products`;
    console.log(`Perform ${req} in uri: ${productsUrl}`);
    const res = requests[req](productsUrl);
    
    check(res, {
        'status is 200': (r) => r.status === 200,
      });
}