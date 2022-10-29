
--
-- TOC entry 210 (class 1259 OID 48427)
-- Name: basket; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.basket (
    id character varying(255) NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 48432)
-- Name: customer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customer (
    id character varying(255) NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    role character varying(255),
    basket_id character varying(255)
);


CREATE TABLE public.invoice (
    id character varying(255) NOT NULL,
    sum integer NOT NULL,
    customer_id character varying(255) NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 48446)
-- Name: product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product (
    id character varying(255) NOT NULL,
    count integer NOT NULL,
    description character varying(255),
    img_url character varying(255),
    name character varying(255),
    price integer NOT NULL,
    product_technology integer,
    product_type integer
);

INSERT INTO public.product VALUES ('9009b625-af8f-4a33-86b7-ef429119f2a4', 10, 'Wired indoor motion, shock, and tilt detector', 'https://ptb.in.ua/image/catalog/vk/ajax/doorprotectfibrablack.jpg', 'DoorProtect Plus Fibra', 500, 3, 3);
INSERT INTO public.product VALUES ('5ffc8b23-f3a6-43bc-84ac-17579a045e85', 15, 'Wired indoor motion and glass break detector', 'https://worldvision.com.ua/content/images/11/480x480l50nn0/ajax-combiprotect-fibra-89214376144733.jpg', 'CombiProtect Fibra', 99, 3, 2);
INSERT INTO public.product VALUES ('75bfc3f9-f728-4db6-b8cd-4c97ba7944b3', 10, 'Wired indoor motion detector with photo verification', 'https://worldvision.com.ua/content/images/17/480x480l50nn0/ajax-motioncam-fibra-96993636271176.jpg', 'Motion cam fibra', 149, 3, 2);
INSERT INTO public.product VALUES ('d6a39bcf-50f5-4b05-89f8-4ad6acbd4878', 9, 'Wired indoor keyboard', 'https://normalnodelai.com.ua/image/cache/catalog/image/products/all/ohrana/5/image-catalog-upload-up11-KeyPad-Fibra-wt-450x450.png', 'KeyPad Fibra', 79, 3, 5);
INSERT INTO public.product VALUES ('4d9a903d-fd6e-4f36-b127-b397c54da5d4', 7, 'Wired siren for indoor and outdoor use', 'https://cdn.secur.ua/media/catalog/product/cache/1/image/500x500/9df78eab33525d08d6e5fb8d27136e95/s/t/streetsiren_fibra_wh_1.png', 'StreetSiren Fibra', 249, 3, 4);
INSERT INTO public.product VALUES ('fbe5abdf-64fb-42fd-ace0-cc8068986489', 45, 'Dual-tech PIR+MW motion detector with IR anti-masking function', 'https://www.satel.eu/img/products/SLIM-DUAL-PRO.jpg', 'SLIM-DUAL-PRO', 49, 1, 0);
INSERT INTO public.product VALUES ('41b01385-d296-43a0-9af5-3fb02c05fede', 25, 'Outdoor PIR + MW dual technology motion detector', 'https://www.satel.eu/img/products/xl/OPAL.jpg', 'Opal', 39, 1, 2);
INSERT INTO public.product VALUES ('96d3aedc-cd40-4b3b-9205-093046ecbecf', 13, 'Digital dual technology motion detector', 'https://www.satel.eu/img/products/xl/COBALT.jpg', 'Cobalt', 49, 1, 2);
INSERT INTO public.product VALUES ('f2e931ec-4a69-4fbb-bffe-0ad814d458a8', 15, 'EN50131 Grade 2 compliant siren with optical signalling', 'https://www.satel.eu/img/products/xl/SP-6500%20R.jpg', 'SP-6500 R', 149, 1, 4);
INSERT INTO public.product VALUES ('34f22e06-def4-4a5f-9119-53137b50ca91', 7, 'EN50131 Grade 2 compliant siren with optical signalling', 'https://www.satel.eu/img/products/xl/SP-4004%20R.jpg', 'SP-4004 R', 49, 1, 4);
INSERT INTO public.product VALUES ('27176b9a-5b5f-4855-a550-6c99c94b726f', 13, 'Indoor siren with acoustical signaling', 'https://www.satel.eu/img/products/xl/SPW-100.jpg', 'SPW-100', 49, 1, 4);
INSERT INTO public.product VALUES ('b4e928a4-62b2-4e1b-b542-c28636d46045', 7, '7" touchscreen keypad', 'https://www.satel.eu/img/products/xl/INT-TSH2-W.jpg', 'INT-TSH2-W', 249, 1, 5);
INSERT INTO public.product VALUES ('06ecbadf-f470-4702-89c6-3e3f56c53a8d', 8, 'Touchscreen keypad', 'https://www.satel.eu/img/products/xl/INT-TSG-B.jpg', 'INT-TSG-B', 216, 1, 5);
INSERT INTO public.product VALUES ('1bee3eb7-b866-4a38-83bb-649af20e1684', 13, 'Touch keypad', 'https://www.satel.eu/img/products/xl/INT-KSG-BSB.jpg', 'INT-KSG-BSB', 349, 1, 5);
INSERT INTO public.product VALUES ('c9c247e6-d9b3-4adc-83fd-4ccecd2bff0f', 11, 'Advanced control panel with 16 up to 128 zones', 'https://www.satel.eu/img/products/xl/INTEGRA%20128.jpg', 'INTEGRA 128', 440, 1, 1);
INSERT INTO public.product VALUES ('f4dba204-222c-4a48-9243-721bc81dce88', 15, 'Alarm control panel mainboard, from 16 to 256 zones and outputs, compliant with the GRADE 3 standard', 'https://www.satel.eu/img/products/xl/INTEGRA%20256%20PLUS.jpg', 'INTEGRA 256 Plus', 649, 1, 5);
INSERT INTO public.product VALUES ('42df4b49-f182-4679-a98d-a82878098b83', 5, 'alarm module with GSM/GPRS communicator', 'https://www.satel.eu/img/products/xl/MICRA.jpg', 'MICRA', 149, 1, 1);
INSERT INTO public.product VALUES ('9198dcd6-fe27-4d24-946d-6f024b198916', 0, '', '', '1', 500, 1, 1);
INSERT INTO public.product VALUES ('f4bd2de0-1bd1-4ecd-988e-260ed31b78f6', 0, '', '', '', 500, 1, 1);
--
-- TOC entry 214 (class 1259 OID 48453)
-- Name: product_basket; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_basket (
    product_id character varying(255) NOT NULL,
    basket_id character varying(255) NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 48458)
-- Name: product_invoice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.product_invoice (
    product_id character varying(255) NOT NULL,
    invoice_id character varying(255) NOT NULL
);


--
-- TOC entry 3345 (class 0 OID 48427)
-- Dependencies: 210
-- Data for Name: basket; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3346 (class 0 OID 48432)
-- Dependencies: 211
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3344 (class 0 OID 48418)
-- Dependencies: 209
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3347 (class 0 OID 48439)
-- Dependencies: 212
-- Data for Name: invoice; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3348 (class 0 OID 48446)
-- Dependencies: 213
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3349 (class 0 OID 48453)
-- Dependencies: 214
-- Data for Name: product_basket; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3350 (class 0 OID 48458)
-- Dependencies: 215
-- Data for Name: product_invoice; Type: TABLE DATA; Schema: public; Owner: -
--



-- Completed on 2022-10-29 03:23:40

--
-- PostgreSQL database dump complete
--

